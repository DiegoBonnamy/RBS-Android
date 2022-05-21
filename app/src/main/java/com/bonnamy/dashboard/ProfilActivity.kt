package com.bonnamy.dashboard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.preference.PreferenceManager
import com.bonnamy.dashboard.bo.Athlete
import com.bonnamy.dashboard.helpers.ReseauHelper
import com.bonnamy.dashboard.ws.RetrofitSingleton
import com.bonnamy.dashboard.ws.WSInterface
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.round

class ProfilActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ATHLETE_ID = "athleteId"
        const val URL_GRAPH_DISTANCE = "https://rbs.diegobonnamy.fr/webview/graphComparaisonDistance"
        const val URL_GRAPH_DISTANCE_CUMUL = "https://rbs.diegobonnamy.fr/webview/graphComparaisonDistanceCumul"
        const val URL_GRAPH_SAISON = "https://rbs.diegobonnamy.fr/webview/graphSaisons"
    }

    private var athleteId = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        setTitle("Profil");

        val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        this.athleteId = intent.getIntExtra(EXTRA_ATHLETE_ID,1)

        val imgProfil: ImageView = findViewById(R.id.img_profil)

        val progressBarCyclisme: ProgressBar = findViewById(R.id.progressBar_cyclisme)
        progressBarCyclisme.getProgressDrawable().setColorFilter(
            Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN
        );

        val progressBarRunning: ProgressBar = findViewById(R.id.progressBar_running)
        progressBarRunning.getProgressDrawable().setColorFilter(
            Color.RED, android.graphics.PorterDuff.Mode.SRC_IN
        );

        val txtDureeTotale: TextView = findViewById(R.id.txt_dureeTotale)
        val txtDistanceTotale: TextView = findViewById(R.id.txt_distanceTotale)
        val txtDeniveleTotal: TextView = findViewById(R.id.txt_deniveleTotal)

        val switchSport: SwitchCompat = findViewById(R.id.switch_sport)
        val txtSport: TextView = findViewById(R.id.txt_sport)

        /* STATS */
        if (ReseauHelper.estConnecte(this)) {
            CoroutineScope(Dispatchers.IO).launch {
                val athlete: Athlete = service.getAthlete(athleteId)

                withContext(Dispatchers.Main) {
                    /* PHOTO */
                    val uiHandler = Handler(Looper.getMainLooper())
                    uiHandler.post(Runnable {
                        Picasso.get()
                            .load(athlete.photo)
                            .into(imgProfil)
                    })

                    /* STATS */
                    txtDureeTotale.text = round(athlete.dureeTotale).toInt().toString() + " h"
                    txtDistanceTotale.text = round(athlete.distanceTotale).toInt().toString() + " km"
                    txtDeniveleTotal.text = round(athlete.deniveleTotal).toInt().toString() + " m"
                }
            }
        }

        /* WEBVIEW */
        if (ReseauHelper.estConnecte(this)) {

            var sport = ""
            if(switchSport.isChecked){
                sport = "Running"
            }
            else{
                sport = "Cyclisme"
            }

            loadWebView(athleteId, sport)

            switchSport.setOnClickListener {
                if (switchSport.isChecked) {
                    sport = "Running"
                    loadWebView(athleteId, sport)
                } else {
                    sport = "Cyclisme"
                    loadWebView(athleteId, sport)
                }
                txtSport.text = sport
            }
        }

    }

    fun loadWebView(athleteId: Int, sport: String){

        // Graph Distance
        val webViewGraphDistance: WebView = findViewById(R.id.webView_graphDistance)
        webViewGraphDistance.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webViewGraphDistance.getSettings().setLoadWithOverviewMode(true);
        webViewGraphDistance.getSettings().setUseWideViewPort(true)
        webViewGraphDistance.getSettings().setJavaScriptEnabled(true)
        webViewGraphDistance.loadUrl(URL_GRAPH_DISTANCE + "?a=" + athleteId + "&s=" + sport)

        // Graph Cumul
        val webView_graphDistanceCumul: WebView = findViewById(R.id.webView_graphDistanceCumul)
        webView_graphDistanceCumul.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView_graphDistanceCumul.getSettings().setLoadWithOverviewMode(true);
        webView_graphDistanceCumul.getSettings().setUseWideViewPort(true)
        webView_graphDistanceCumul.getSettings().setJavaScriptEnabled(true)
        webView_graphDistanceCumul.loadUrl(URL_GRAPH_DISTANCE_CUMUL + "?a=" + athleteId + "&s=" + sport)

        // Graph Saisons
        val webView_graphSaison: WebView = findViewById(R.id.webView_graphSaison)
        webView_graphSaison.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView_graphSaison.getSettings().setLoadWithOverviewMode(true);
        webView_graphSaison.getSettings().setUseWideViewPort(true)
        webView_graphSaison.getSettings().setJavaScriptEnabled(true)
        webView_graphSaison.loadUrl(URL_GRAPH_SAISON + "?a=" + athleteId + "&s=" + sport)
    }

    override fun onResume() {
        super.onResume()

        val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        val progressBarCyclisme: ProgressBar = findViewById(R.id.progressBar_cyclisme)
        val progressBarRunning: ProgressBar = findViewById(R.id.progressBar_running)

        /* STATS */
        if (ReseauHelper.estConnecte(this)) {
            CoroutineScope(Dispatchers.IO).launch {
                val athlete: Athlete = service.getAthlete(athleteId)

                withContext(Dispatchers.Main) {

                    /* PROGRESS BAR */
                    progressBarCyclisme.max = round(athlete.objectifCyclisme.toFloat()).toInt()
                    progressBarCyclisme.progress = round(athlete.distanceCyclisme).toInt()
                    progressBarRunning.max = round(athlete.objectifRunning.toFloat()).toInt()
                    progressBarRunning.progress = round(athlete.distanceRunning).toInt()
                }
            }
        }

        /* SWITCH */
        val switchSport: SwitchCompat = findViewById(R.id.switch_sport)
        val sportPreference: Int = preferences.getInt(SettingsActivity.CLE_PREFERENCES, 0)
        switchSport.isChecked = false
        if(sportPreference == 1){
            switchSport.isChecked = true
        }
        var sport = ""
        if(switchSport.isChecked){
            sport = "Running"
        }
        else{
            sport = "Cyclisme"
        }
        val txtSport: TextView = findViewById(R.id.txt_sport)
        txtSport.text = sport

        /* WEBVIEW */
        if (ReseauHelper.estConnecte(this)) {
            loadWebView(athleteId, sport)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profil_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                intent.putExtra(SettingsActivity.EXTRA_ATHLETE_ID, this.athleteId)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}