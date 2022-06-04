package com.dixa.basearchitecture.extension

import androidx.activity.ComponentActivity
import androidx.lifecycle.LiveData

fun<T> ComponentActivity.observe(liveData: LiveData<T>?, observer: ((T)->Unit)) {
    liveData?.observe(this) { observer(it) }
}
