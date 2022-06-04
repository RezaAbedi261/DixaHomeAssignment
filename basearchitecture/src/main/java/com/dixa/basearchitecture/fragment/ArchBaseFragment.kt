package com.dixa.basearchitecture.fragment

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dixa.basearchitecture.extension.observe
import com.dixa.basearchitecture.setView
import com.dixa.basearchitecture.setVm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel


abstract class ArchBaseFragment<B : ViewDataBinding, VM : ViewModel> : Fragment(),
    CoroutineScope by CoroutineScope(Dispatchers.Main) {


    @LayoutRes
    abstract fun layout(): Int
    protected abstract val viewModel: VM

    /**
     * onBackPressedDispatcher callback will be registered only if this value is true
     */
    protected open val registerOnBackPressedDispatcher: Boolean = false

    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout(), container, false)

        binding.lifecycleOwner = this

        binding.setView(this)
        binding.setVm(viewModel)
        onBindingCreated(savedInstanceState)
        registerObservers()

        return binding.root
    }

    open fun registerObservers() {}

    open fun onBindingCreated(savedInstanceState: Bundle?) {}

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    operator fun <T> LiveData<T>.invoke(observer: ((T) -> Unit)) {
        observe(this, observer)
    }

    open fun onBackPressed() = false

}
