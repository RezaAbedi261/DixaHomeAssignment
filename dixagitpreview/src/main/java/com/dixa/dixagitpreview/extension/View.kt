package com.dixa.dixagitpreview.extension

import android.view.View
import android.view.ViewTreeObserver

fun View.onceOnGlobalLayout(onGlobalLayout: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            try {
                viewTreeObserver.removeOnGlobalLayoutListener(this);
                onGlobalLayout();
            } catch (ignored: Exception) {
            }
        }
    })
}

fun View.observeGlobalLayoutOnce(observer: () -> Unit) =
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        override fun onGlobalLayout() {
            try {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                observer()
            } catch (ignored: Exception) {
            }
        }
    })
