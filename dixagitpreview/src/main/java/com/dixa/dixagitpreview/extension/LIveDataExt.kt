package com.dixa.dixagitpreview.extension

import android.os.Looper
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.emit(value: T?): MutableLiveData<T> {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        setValue(value)
    } else {
        postValue(value)
    }
    return this
}
