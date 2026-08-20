package com.muyeedlab.agecalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var adView: AdView
    private lateinit var consentInformation: ConsentInformation
    private lateinit var privacyOptions: TextView
    private var adsRequested = false

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        webView = WebView(this).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.allowFileAccess = true
            settings.allowContentAccess = true
            webViewClient = WebViewClient()
            loadUrl("file:///android_asset/index.html")
        }

        privacyOptions = TextView(this).apply {
            text = "Privacy options"
            textSize = 13f
            setPadding(16, 8, 16, 8)
            visibility = View.GONE
            setOnClickListener {
                UserMessagingPlatform.showPrivacyOptionsForm(this@MainActivity) { }
            }
        }

        adView = AdView(this).apply {
            // Google-provided test banner ID. Replace with your own AdMob unit ID for production.
            adUnitId = "ca-app-pub-3940256099942544/9214589747"
            setAdSize(AdSize.BANNER)
        }

        root.addView(
            webView,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )
        root.addView(
            privacyOptions,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        root.addView(
            adView,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        setContentView(root)
        requestConsentAndLoadAds()
    }

    private fun requestConsentAndLoadAds() {
        consentInformation = UserMessagingPlatform.getConsentInformation(this)
        val params = ConsentRequestParameters.Builder().build()

        consentInformation.requestConsentInfoUpdate(
            this,
            params,
            {
                privacyOptions.visibility = if (
                    consentInformation.privacyOptionsRequirementStatus ==
                    ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED
                ) View.VISIBLE else View.GONE

                UserMessagingPlatform.loadAndShowConsentFormIfRequired(this) {
                    loadAdsIfAllowed()
                }
            },
            {
                loadAdsIfAllowed()
            }
        )

        // This also covers users who already have a valid consent state from a previous session.
        loadAdsIfAllowed()
    }

    private fun loadAdsIfAllowed() {
        if (adsRequested || !consentInformation.canRequestAds()) return
        adsRequested = true
        MobileAds.initialize(this) {
            adView.loadAd(AdRequest.Builder().build())
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        adView.destroy()
        webView.destroy()
        super.onDestroy()
    }
}
