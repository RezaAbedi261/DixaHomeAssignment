package com.dixa.dixagitpreview.data.exception

@Throws
inline fun <reified T : Throwable> throwIf(throwable: T, condition: () -> Boolean) {
    if (condition()) throw throwable else return
}
