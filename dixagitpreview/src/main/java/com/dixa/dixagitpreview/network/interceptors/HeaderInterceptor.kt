package com.dixa.dixagitpreview.network.interceptors

import android.os.Build
import com.dixa.dixagitpreview.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().let {
            val url = it.url().newBuilder()
                .host(it.url().host())
                .port(it.url().port())
                .build()
            it.newBuilder()
                .url(url)
//                .addHeader(Constants.Headers.APP_VERSION, BuildConfig.v)
//                .addHeader(Constants.Headers.APP_VERSION_CODE, BuildConfig.VERSION_CODE.toString())
                .addHeader(Constants.Headers.PLATFORM, Constants.PLATFORM)
                .addHeader(Constants.Headers.OS_VERSION, "${Build.VERSION.SDK_INT}")
                .addHeader(Constants.Headers.OS_RELEASE, Build.VERSION.RELEASE)
                .build()
        }
        return chain.proceed(newRequest)
    }
}