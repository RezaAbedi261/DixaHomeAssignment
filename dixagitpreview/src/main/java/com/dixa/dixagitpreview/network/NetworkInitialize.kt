package com.dixa.dixagitpreview.network

import com.dixa.dixagitpreview.BuildConfig
import com.dixa.dixagitpreview.network.interceptors.HeaderInterceptor
import com.dixa.dixagitpreview.util.Constants
import com.dixa.dixagitpreview.util.Constants.APPLICATION_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

object NetworkInitialize {

    val appApi = provideApi<AppApi>(provideRetrofit(GsonConverterFactory.create(GsonBuilder().create())))

    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().apply {
            addInterceptor(HeaderInterceptor())
        }
            .connectTimeout(Constants.CONNECTION_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(Constants.READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .proxy(Proxy.NO_PROXY)
            .run {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                }
                build()
            }
    }

    fun provideRetrofit(
        converterFactory: Converter.Factory,
    ): Retrofit {
        val client = provideOkHttpClient()
        return Retrofit.Builder()
            .baseUrl(APPLICATION_URL)
            .client(client)
            .addConverterFactory(converterFactory).build()
    }

    inline fun <reified API> provideApi(retrofit: Retrofit) = retrofit.create(API::class.java)


}