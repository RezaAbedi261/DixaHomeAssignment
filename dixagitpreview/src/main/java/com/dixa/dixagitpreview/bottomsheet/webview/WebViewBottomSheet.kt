package com.dixa.dixagitpreview.bottomsheet.webview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.dixa.basearchitecture.viewmodel.BaseViewModel
import com.dixa.dixagitpreview.R
import com.dixa.dixagitpreview.bottomsheet.BaseBottomSheet
import com.dixa.dixagitpreview.databinding.BottomsheetWebviewBinding

class WebViewBottomSheet(val url: String) : BaseBottomSheet<BottomsheetWebviewBinding, EmptyViewModel>() {

    override fun layout() = R.layout.bottomsheet_webview
    private var webView: WebView? = null

    override fun isFullScreen() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableDrag()

        webView = binding.webView
        webView?.loadUrl(url)
        webView?.settings?.domStorageEnabled = true
        webView?.settings?.javaScriptEnabled = true


        webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }

    }

    override fun onResume() {
        super.onResume()
        disableDrag()
    }


    override fun registerObservers() {}

}

class EmptyViewModel: BaseViewModel() {

}