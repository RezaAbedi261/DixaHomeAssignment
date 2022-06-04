package com.dixa.basearchitecture.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <A, B> LiveData<A>.zip(second: LiveData<B>): LiveData<Pair<A, B>> {
    val mediatorLiveData = MediatorLiveData<Pair<A, B>>()

    var isFirstEmitted = false
    var isSecondEmitted = false
    var firstValue: A? = null
    var secondValue: B? = null

    mediatorLiveData.addSource(this) {
        isFirstEmitted = true
        firstValue = it
        if (isSecondEmitted) {
            mediatorLiveData.value = Pair(firstValue!!, secondValue!!)
            isFirstEmitted = false
            isSecondEmitted = false
        }
    }
    mediatorLiveData.addSource(second) {
        isSecondEmitted = true
        secondValue = it
        if (isFirstEmitted) {
            mediatorLiveData.value = Pair(firstValue!!, secondValue!!)
            isFirstEmitted = false
            isSecondEmitted = false
        }
    }

    return mediatorLiveData
}