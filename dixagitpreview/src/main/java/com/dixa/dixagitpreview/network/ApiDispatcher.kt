package com.dixa.dixagitpreview.network

import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.WorkerThread
import com.dixa.dixagitpreview.data.exception.toError
import com.dixa.dixagitpreview.data.model.Resource
import com.dixa.dixagitpreview.util.Constants
import retrofit2.Call
import retrofit2.HttpException
import com.dixa.dixagitpreview.data.exception.Error


open class ApiDispatcher {

    var appApi: AppApi = NetworkInitialize.appApi

    @WorkerThread
    @CallSuper
    open fun <RESULT> sync(
        apiCall: ApiDispatcher.() -> Call<RESULT?>,
    ): Resource<RESULT?> {
        return try {
            val response = apiCall(this).execute()
            if (!response.isSuccessful){
                throw HttpException(response)
            }
            val body = response.body()
            onSuccess(body)
            Resource.success(body)
        } catch (e: HttpException) {
            Resource.error(null)
        }
    }

    @WorkerThread
    @CallSuper
    open suspend fun <RESULT> suspend(
        apiCall: suspend ApiDispatcher.() -> RESULT?,
    ): Resource<RESULT?> {
        return try {
            val response = apiCall(this)
            Resource.success(response)
        } catch (e: HttpException) {
            Resource.error(null)
        }
    }

    private fun provideError(throwable: Throwable): Error {
        Log.e(Constants.Tags.NETWORK, "api error:", throwable)
        return throwable.toError()
    }

    private fun <T> onSuccess(response : T){
        Log.d(Constants.Tags.NETWORK, "response: $response")
    }

}