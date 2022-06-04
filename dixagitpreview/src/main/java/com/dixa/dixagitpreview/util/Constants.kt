package com.dixa.dixagitpreview.util

object Constants {

    const val CONNECTION_TIMEOUT_MILLIS = 30_000L
    const val READ_TIMEOUT_MILLIS = 30_000L
    const val APPLICATION_URL = "https://api.github.com/"
    const val PLATFORM = "android"


    object Headers {
        const val APP_VERSION = "appVersion"
        const val PLATFORM = "platform"
        const val OS_VERSION = "osVersion"
        const val OS_RELEASE = "osRelease"
        const val APP_VERSION_CODE = "appVersionCode"
    }

    object Tags {
        const val APP = "DixaGitPreview"
        const val NETWORK = "${APP}_network"
    }
}