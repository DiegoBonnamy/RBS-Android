package com.bonnamy.dashboard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.bonnamy.dashboard.bo.DistanceTotale
import com.bonnamy.dashboard.bo.Medals
import com.bonnamy.dashboard.bo.Mvp
import com.bonnamy.dashboard.helpers.ReseauHelper
import com.bonnamy.dashboard.ws.RetrofitSingleton
import com.bonnamy.dashboard.ws.WSInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    companion object{
        const val URL_GRAPH_FORME = "https://rbs.diegobonnamy.fr/webview/graphForme"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("Dashboard")

        val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)

        /* DISTANCE */

        val txtDistanceYear: TextView = findViewById(R.id.txt_distanceYear)

        if (ReseauHelper.estConnecte(this)){
            CoroutineScope(Dispatchers.IO).launch {
                val distanceTotale: DistanceTotale = service.getDistance()

                withContext(Dispatchers.Main){
                    txtDistanceYear.text = round(distanceTotale.distance.toDouble()).toInt().toString() + " km"
                }
            }
        }
        else{
            txtDistanceYear.text = "Aucune connexion internet"
        }

        /* MVP */

        val txtMvpName: TextView = findViewById(R.id.txt_mvpName)
        val txtMvpPts: TextView = findViewById(R.id.txt_mvpPts)
        val txtMvpDistance: TextView = findViewById(R.id.txt_mvpDistance)
        val imgMvp: ImageView = findViewById(R.id.img_mvp)

        if (ReseauHelper.estConnecte(this)) {
            CoroutineScope(Dispatchers.IO).launch {
                val mvp: Mvp = service.getMvp()

                withContext(Dispatchers.Main) {
                    txtMvpName.text = mvp.prenom
                    txtMvpPts.text = mvp.pts + " pts"

                    val uiHandler = Handler(Looper.getMainLooper())
                    uiHandler.post(Runnable {
                        Picasso.get()
                            .load(mvp.photo)
                            .into(imgMvp)
                    })

                    var distanceMvp = ""
                    if (mvp.distanceCyclisme.toFloat() > 0) {
                        distanceMvp += "\nCyclisme : " + mvp.distanceCyclisme + " km"
                    }
                    if (mvp.distanceRunning.toFloat() > 0) {
                        distanceMvp += "\nRunning : " + mvp.distanceRunning + " km"
                    }
                    if (mvp.distanceMarche.toFloat() > 0) {
                        distanceMvp += "\nMarche : " + mvp.distanceMarche + " km"
                    }

                    txtMvpDistance.text = distanceMvp
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)

        /* WEBVIEW */

        if (ReseauHelper.estConnecte(this)) {
            val webViewGraphEfforts: WebView = findViewById(R.id.webView_graphEfforts)
            webViewGraphEfforts.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            webViewGraphEfforts.getSettings().setLoadWithOverviewMode(true);
            webViewGraphEfforts.getSettings().setUseWideViewPort(true)
            webViewGraphEfforts.getSettings().setJavaScriptEnabled(true)
            webViewGraphEfforts.loadUrl(URL_GRAPH_FORME)
        }

        /* MEDALS */

        val txtPts1: TextView = findViewById(R.id.txt_pts1)
        val txtPts2: TextView = findViewById(R.id.txt_pts2)
        val txtPts3: TextView = findViewById(R.id.txt_pts3)
        val txtPts4: TextView = findViewById(R.id.txt_pts4)

        val listTextView: MutableList<TextView> = ArrayList()
        listTextView.add(txtPts1)
        listTextView.add(txtPts2)
        listTextView.add(txtPts3)
        listTextView.add(txtPts4)

        if (ReseauHelper.estConnecte(this)) {
            CoroutineScope(Dispatchers.IO).launch {
                val medalsArray: Array<Medals> = service.getMedals()

                // Détermination du max
                var max = -1F
                for (medals in medalsArray) {
                    if (medals.pts.toFloat() >= max) {
                        max = medals.pts.toFloat()
                    }
                }

                // Mise en forme
                withContext(Dispatchers.Main) {
                    for (medals in medalsArray) {

                        val textViewMedal: TextView = listTextView[medals.id.toInt() - 1]

                        textViewMedal.text = medals.pts
                        textViewMedal.setBackgroundColor(Color.parseColor(medals.couleur))

                        // Margin top
                        val marginTop = round(250 - (medals.pts.toFloat() / max) * 250)
                        val param = textViewMedal.layoutParams as ViewGroup.MarginLayoutParams
                        param.setMargins(0, marginTop.toInt(), 0, 0)
                        textViewMedal.layoutParams = param
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_records -> {
                val intent = Intent(this, RecordsListActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_segments -> {
                val intent = Intent(this, SegmentsMenuActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_map -> {
                val intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_calendrier -> {
                val intent = Intent(this, EventsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_profil1 -> {
                val intent = Intent(this, ProfilActivity::class.java)
                intent.putExtra(ProfilActivity.EXTRA_ATHLETE_ID, 1)
                startActivity(intent)
                true
            }
            R.id.action_profil2 -> {
                val intent = Intent(this, ProfilActivity::class.java)
                intent.putExtra(ProfilActivity.EXTRA_ATHLETE_ID, 2)
                startActivity(intent)
                true
            }
            R.id.action_profil3 -> {
                val intent = Intent(this, ProfilActivity::class.java)
                intent.putExtra(ProfilActivity.EXTRA_ATHLETE_ID, 3)
                startActivity(intent)
                true
            }
            R.id.action_profil4 -> {
                val intent = Intent(this, ProfilActivity::class.java)
                intent.putExtra(ProfilActivity.EXTRA_ATHLETE_ID, 4)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}