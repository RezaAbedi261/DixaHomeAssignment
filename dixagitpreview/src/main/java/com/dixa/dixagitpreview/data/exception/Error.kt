package com.dixa.dixagitpreview.data.exception

import com.google.gson.JsonParseException
import retrofit2.HttpException

abstract class Error(val throwable: Throwable) {

    class NetworkUnavailable(throwable: Throwable = NetworkUnavailableException()) : Error(throwable)

    class JsonParsing(throwable: Throwable) : Error(throwable)

    open class Http(throwable: HttpException) : Error(throwable) {
        val code: Int
            get() = (throwable as HttpException).code()
    }

    class Unknown(throwable: Throwable) : Error(throwable)
}

fun Throwable.toError() = when (this) {
    is NetworkUnavailableException -> Error.NetworkUnavailable(this)
    is JsonParseException -> Error.JsonParsing(this)
    is HttpException -> {
        when (code()) {
            else -> Error.Http(this)
        }
    }
    else -> Error.Unknown(this)
}