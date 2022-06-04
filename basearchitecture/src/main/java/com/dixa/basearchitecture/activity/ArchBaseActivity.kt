package com.dixa.basearchitecture.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dixa.basearchitecture.extension.observe
import com.dixa.basearchitecture.setView
import com.dixa.basearchitecture.setVm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class ArchBaseActivity<B : ViewDataBinding, VM : ViewModel> : AppCompatActivity() ,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    @LayoutRes abstract fun layout(): Int
    protected abstract val viewModel: VM

    lateinit var binding: B


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout())

        binding.lifecycleOwner = this

        binding.setView(this)
        binding.setVm(viewModel)
        onBindingCreated(savedInstanceState)
        registerObservers()
    }

    open fun registerObservers(){}

    open fun onBindingCreated(savedInstanceState: Bundle?){}



    operator fun<T> LiveData<T>.invoke(observer: (T)->Unit){
        observe(this, observer)
    }

}