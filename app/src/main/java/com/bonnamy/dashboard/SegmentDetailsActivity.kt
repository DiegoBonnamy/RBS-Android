package com.bonnamy.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bonnamy.dashboard.adapters.SegmentTimeAdapter
import com.bonnamy.dashboard.bo.AthleteTime
import com.bonnamy.dashboard.bo.Segment
import com.bonnamy.dashboard.bo.SegmentTime
import com.bonnamy.dashboard.helpers.ReseauHelper
import java.sql.Time
import java.util.*
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class SegmentDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SEGMENT = "segmentClick"
    }

    lateinit var timesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segment_details)

        setTitle("Details")

        val segment: Segment? = intent.getParcelableExtra(EXTRA_SEGMENT)

        // Nom du segment
        val txtSegName: TextView = findViewById(R.id.txt_segName)
        txtSegName.text = segment?.nom

        // Icon de la categorie

        val imgCat: ImageView = findViewById(R.id.img_segIconCat)
        val txtCat: TextView = findViewById(R.id.txt_segCat)

        if(segment != null){
            if(segment.type == "Ascension"){
                val cat = when(round(segment.distance.toInt() * segment.pente.toDouble()).toInt()){
                    in 0..2300 -> "4"
                    in 2300..2600 -> "3"
                    in 2600..2900 -> "2"
                    in 2900..3200 -> "1"
                    else -> "HC"
                }
                txtCat.text = cat

                when(cat){
                    "4" -> imgCat.setBackgroundResource(R.drawable.icon_cat_4)
                    "3" -> imgCat.setBackgroundResource(R.drawable.icon_cat_3)
                    "2" -> imgCat.setBackgroundResource(R.drawable.icon_cat_2)
                    "1" -> imgCat.setBackgroundResource(R.drawable.icon_cat_1)
                    else -> imgCat.setBackgroundResource(R.drawable.icon_cat_hc)
                }
            }
            else{
                val cat = when(round(segment.distance.toInt() * segment.pente.toDouble()).toInt()){
                    in 0..300 -> "3"
                    in 300..600 -> "2"
                    else -> "1"
                }
                txtCat.text = cat

                when(cat){
                    "3" -> imgCat.setBackgroundResource(R.drawable.icon_sprint_3)
                    "2" -> imgCat.setBackgroundResource(R.drawable.icon_sprint_2)
                    else -> imgCat.setBackgroundResource(R.drawable.icon_sprint_1)
                }

                val layoutParams = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
                txtCat.layoutParams = layoutParams
            }
        }

        // Distance du segment
        val txtSegDistance: TextView = findViewById(R.id.txt_segDistance)
        txtSegDistance.text = segment?.distance + "m"

        // Pente du segment
        val txtSegPente: TextView = findViewById(R.id.txt_segPente)
        txtSegPente.text = segment?.pente + "%"

        // Profil du segment
        val webViewProfil: WebView = findViewById(R.id.webView_segProfil)
        if(segment != null){
            val urlProfil: String = "https://veloviewer.com/segments/" + segment.id_strava + "/embed?default2d=y"
            if (ReseauHelper.estConnecte(this)) {
                webViewProfil.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                        view?.loadUrl(url)
                        return true
                    }
                }
                webViewProfil.getSettings().setLoadWithOverviewMode(true);
                webViewProfil.getSettings().setUseWideViewPort(true)
                webViewProfil.getSettings().setJavaScriptEnabled(true)
                webViewProfil.loadUrl(urlProfil)
            }
        }

        // Liste des temps
        if(segment != null){
            this.timesRecyclerView = findViewById(R.id.bloc_segTimes)
            timesRecyclerView.setHasFixedSize(true)
            timesRecyclerView.layoutManager = LinearLayoutManager(this)

            val timesList: MutableList<SegmentTime> = mutableListOf()
            val timesOrderList: Map<String, Int> = mapOf(
                segment.temps1.prenom to segment.temps1.temps.toInt(),
                segment.temps2.prenom to segment.temps2.temps.toInt(),
                segment.temps3.prenom to segment.temps3.temps.toInt(),
                segment.temps4.prenom to segment.temps4.temps.toInt()
            ).toList().sortedBy { (_, value) -> value}.toMap()

            // Ajout
            for (time in timesOrderList){
                when(time.key){
                    segment.temps1.prenom -> timesList.add(
                        SegmentTime(
                            segment.temps1.photo,
                            segment.temps1.prenom,
                            segment.temps1.temps.toInt(),
                            ((segment.distance.toDouble() / segment.temps1.temps.toDouble() * 3.6) * 10.0).roundToInt() / 10.0
                        )
                    )
                    segment.temps2.prenom -> timesList.add(
                        SegmentTime(
                            segment.temps2.photo,
                            segment.temps2.prenom,
                            segment.temps2.temps.toInt(),
                            ((segment.distance.toDouble() / segment.temps2.temps.toDouble() * 3.6) * 10.0).roundToInt() / 10.0
                        )
                    )
                    segment.temps3.prenom -> timesList.add(
                        SegmentTime(
                            segment.temps3.photo,
                            segment.temps3.prenom,
                            segment.temps3.temps.toInt(),
                            ((segment.distance.toDouble() / segment.temps3.temps.toDouble() * 3.6) * 10.0).roundToInt() / 10.0
                        )
                    )
                    segment.temps4.prenom -> timesList.add(
                        SegmentTime(
                            segment.temps4.photo,
                            segment.temps4.prenom,
                            segment.temps4.temps.toInt(),
                            ((segment.distance.toDouble() / segment.temps4.temps.toDouble() * 3.6) * 10.0).roundToInt() / 10.0
                        )
                    )
                }
            }

            val segmentTimeAdapter = SegmentTimeAdapter(timesList.toMutableList())
            timesRecyclerView.adapter = segmentTimeAdapter
        }
    }
}