package com.dixa.dixagitpreview.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.ContextCompat


fun Context.isNetworkConnected() =
    ContextCompat.getSystemService(this, ConnectivityManager::class.java)?.run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getNetworkCapabilities(activeNetwork)?.let { networkCapabilities ->
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        } else {
            activeNetworkInfo?.isConnected ?: false
        }
    } ?: false