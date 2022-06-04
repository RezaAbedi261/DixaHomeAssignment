package com.dixa.basearchitecture.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun<T> Fragment.observe(liveData: LiveData<T>?, observer: ((T)->Unit)) {
    liveData?.observe(viewLifecycleOwner) { observer(it) }
}