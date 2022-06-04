package com.dixa.basearchitecture.viewmodel

import androidx.lifecycle.*

abstract class BaseViewModel : ViewModel(), LifecycleOwner {
    private var lifecycleRegistry : LifecycleRegistry? = null

    override fun getLifecycle(): Lifecycle {
        if(lifecycleRegistry == null){
            lifecycleRegistry = LifecycleRegistry(this)
            lifecycleRegistry?.currentState = Lifecycle.State.RESUMED
        }
        return lifecycleRegistry!!
    }

    override fun onCleared() {
        super.onCleared()
        lifecycleRegistry?.currentState = Lifecycle.State.DESTROYED
    }

    protected operator fun <T> LiveData<T>.invoke(observer: ((T) -> Unit)){
        observe(this@BaseViewModel,  Observer{ observer(it) })
    }

}
