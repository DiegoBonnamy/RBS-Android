package com.bonnamy.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bonnamy.dashboard.helpers.ReseauHelper

class MapActivity : AppCompatActivity() {

    companion object {
        const val URL_MAP = "https://rbs.diegobonnamy.fr/map2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        setTitle("Map");

        /* WEBVIEW */
        if (ReseauHelper.estConnecte(this)) {
            val webView_map: WebView = findViewById(R.id.webView_map)
            webView_map.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            webView_map.getSettings().setLoadWithOverviewMode(true);
            webView_map.getSettings().setUseWideViewPort(true)
            webView_map.getSettings().setJavaScriptEnabled(true)
            webView_map.loadUrl(URL_MAP)
        }
    }
}