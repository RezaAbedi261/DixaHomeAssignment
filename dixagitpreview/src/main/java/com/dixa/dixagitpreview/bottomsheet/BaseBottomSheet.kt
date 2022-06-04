package com.dixa.dixagitpreview.bottomsheet

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.OnRebindCallback
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.dixa.basearchitecture.setView
import com.dixa.basearchitecture.viewmodel.BaseViewModel
import com.dixa.dixagitpreview.R
import com.dixa.dixagitpreview.extension.onceOnGlobalLayout
import com.dixa.dixagitpreview.util.PixelUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlin.math.nextDown


abstract class BaseBottomSheet<B : ViewDataBinding, VM : BaseViewModel> : BottomSheetDialogFragment(),
    CoroutineScope by CoroutineScope(Dispatchers.Main){

    @LayoutRes
    abstract fun layout(): Int

    protected lateinit var viewModel: VM
    protected lateinit var binding: B
    var isGoingToBeShown = false
    protected open fun needsDelayedTransitions(): Boolean = true

    open fun getMyTag(): String? {
        return javaClass.toString()
    }

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
        //set animation for all bottomSheets
        dialog?.let {
            it.window?.attributes?.windowAnimations = R.style.DialogAnimation
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.RoundBottomSheet)
    }

    protected open fun isFullScreen() = false

    @CallSuper
    protected open fun onShow() {}

    protected open fun disableDrag(){
        myBehavior?.isDraggable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val ans = super.onCreateDialog(savedInstanceState)
        ans.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        ans.setOnShowListener {
            if(isFullScreen()){
                val parentLayout = (it as? BottomSheetDialog)?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                parentLayout?.let { pl ->
                    pl.layoutParams = pl.layoutParams.apply {
                        height = WindowManager.LayoutParams.MATCH_PARENT
                    }
                    val behaviour = BottomSheetBehavior.from(pl)
                    behaviour.halfExpandedRatio = 1f.nextDown()
                    pl.onceOnGlobalLayout {
                        behaviour.peekHeight = pl.height
                    }
                    behaviour.state = STATE_EXPANDED
                }
            }
            onShow()
        }
        return ans
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.Theme_HomeAssignment)


        val localInflater = inflater.cloneInContext(contextThemeWrapper)

        binding = DataBindingUtil.inflate(localInflater, layout(), container, false)
        binding.lifecycleOwner = this

        binding.setView(this)

        fillSharedViewModels()


        onCreateView(savedInstanceState)
        registerObservers()

        handleKeyboardAndHeightResize()

        addTransitionManagerAutoAnimations()

        return binding.root
    }

    protected open val autoAnimationDuration = 100L
    private val autoAnimationDurationSkip get() = 1000L //autoAnimationDuration + 200L
    private var lastAutoAnimationTime = 0L
    private fun addTransitionManagerAutoAnimations() {
        //https://medium.com/androiddevelopers/android-data-binding-animations-55f6b5956a64
        if (needsDelayedTransitions()) {
            binding.addOnRebindCallback(object : OnRebindCallback<B>() {
                override fun onPreBind(binding: B?): Boolean {
                    val ct = System.currentTimeMillis()
                    if (ct > lastAutoAnimationTime + autoAnimationDurationSkip) {
                        lastAutoAnimationTime = ct
                        (binding?.root as? ViewGroup)?.let {
                            val autoTransition = AutoTransition()
                            autoTransition.duration = autoAnimationDuration
                            TransitionManager.beginDelayedTransition(it, autoTransition)
                        }
                    }
                    return true
                }
            })
        }
    }

    private var rootPadding: IntArray? = null

    var heightErrorDiff = 0
    private val keyboardGlobalLayoutObserver = ViewTreeObserver.OnGlobalLayoutListener {
        val displayFrame = Rect()
        activity?.window?.decorView?.getWindowVisibleDisplayFrame(displayFrame)
        val height: Int = PixelUtil(requireContext()).heightPx
        val heightDifferenceReal: Int = height - displayFrame.bottom
        if (rootPadding == null) {
            rootPadding = intArrayOf(binding.root.paddingLeft, binding.root.paddingTop, binding.root.paddingRight, binding.root.paddingBottom)
        }
        if(heightDifferenceReal < 0){
            heightErrorDiff = -heightDifferenceReal
        }
        val heightDifference: Int = heightDifferenceReal + heightErrorDiff
        if (heightDifference > 0) {
            if (binding.root.paddingBottom != heightDifference) {
                binding.root.setPadding(rootPadding?.get(0) ?: 0, rootPadding?.get(1)
                        ?: 0, rootPadding?.get(2) ?: 0, (rootPadding?.get(3)
                        ?: 0) + if (heightErrorDiff != 0) heightDifference else heightDifference + displayFrame.top)
                myBehavior?.state = STATE_EXPANDED
            }
        } else {
            if (binding.root.paddingBottom != 0) {
                binding.root.setPadding(rootPadding?.get(0) ?: 0, rootPadding?.get(1)
                        ?: 0, rootPadding?.get(2) ?: 0, rootPadding?.get(3) ?: 0)
            }
        }

    }

    private fun handleKeyboardAndHeightResize() {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        activity?.window?.decorView?.viewTreeObserver?.addOnGlobalLayoutListener(keyboardGlobalLayoutObserver)

    }

    open fun fillSharedViewModels() {}
    open fun onCreateView(savedInstanceState: Bundle?) {}
    open fun registerObservers() {}

    override fun onDestroyView() {
        activity?.window?.decorView?.viewTreeObserver?.removeOnGlobalLayoutListener(keyboardGlobalLayoutObserver)
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }


    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    protected var myBehavior: BottomSheetBehavior<*>? = null
        get(){
            if(field == null){
                val behavior = ((binding.root.parent as? View)?.layoutParams as? CoordinatorLayout.LayoutParams)?.behavior
                if (behavior is BottomSheetBehavior<*>) {
                    field = behavior
                }
            }
            return field
        }


    data class BottomSheetStickyViewPair(
            var view: View,
            var offset: Int = 0
    )

    open fun show(manager: FragmentManager) {

        try {
            if (shallBeShown(manager)) {
                isGoingToBeShown = true
                super.show(manager, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun show(
            manager: FragmentManager,
            tag: String?
    ) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.setCustomAnimations(R.anim.fragment_animation_enter_alpha, R.anim.fragment_animation_exit_alpha)
            ft.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }


    @Deprecated("Make sure why are you using this, use `hide` function instead")
    override fun dismiss() {
        try {
            super.dismiss()
            isGoingToBeShown = false
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    open fun hide() {
        try {
            super.dismissAllowingStateLoss()
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun dismissAllowingStateLoss() {
        try {
            super.dismissAllowingStateLoss()
            isGoingToBeShown = false
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    private fun shallBeShown(manager: FragmentManager, tag: String? = null): Boolean {
        if (manager.findFragmentByTag(tag) == this || isAdded || isGoingToBeShown) {
            return false
        }
        return true
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isGoingToBeShown = false
    }

}

