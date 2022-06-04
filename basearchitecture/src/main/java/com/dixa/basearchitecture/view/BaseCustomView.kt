package com.dixa.basearchitecture.view


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dixa.basearchitecture.setView


abstract class BaseCustomView<B : ViewDataBinding> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    @LayoutRes abstract fun layout(): Int

    protected lateinit var binding: B

    init {
        this.inflate(attrs)
    }

    @CallSuper
    protected open fun inflate(attrs: AttributeSet?) {
        if (isInEditMode) {
            LayoutInflater.from(context).inflate(layout(), this, true)
        }
        else{
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), layout(), this, true)
            binding.setView(this)
        }
    }

}