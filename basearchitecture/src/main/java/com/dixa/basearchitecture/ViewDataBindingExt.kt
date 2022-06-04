package com.dixa.basearchitecture

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

fun ViewDataBinding.setView(view: Any){
    try {
        val setViewMethod = this.javaClass.getMethod("setView", view.javaClass)
        setViewMethod.invoke(this, view)
    } catch (ignored: Throwable) {
    }
}
fun ViewDataBinding.setVm(viewModel: ViewModel){
    try {
        val setVmMethod = this.javaClass.getMethod("setVm", viewModel.javaClass)
        setVmMethod.invoke(this, viewModel)
    } catch (ignored: Throwable) {
    }
}