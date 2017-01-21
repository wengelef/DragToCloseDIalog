package com.wengelef.dragtoclosedialog

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseDialogFragment : DialogFragment() {

    private val ANIMATION_DURATION = 200L

    protected var mAnimationListener: DialogAnimationListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(getLayoutResId(), container, false)
        root.setOnClickListener { v -> close() }
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, 0)
    }

    override fun onResume() {
        super.onResume()
        dialog.window?.setLayout(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels)
    }

    override fun onStart() {
        super.onStart()

        val decorView = dialog.window?.decorView

        val fadeIn = ObjectAnimator.ofPropertyValuesHolder(decorView,
                PropertyValuesHolder.ofFloat("alpha", 0f, 1f))

        val scaleUp = ObjectAnimator.ofPropertyValuesHolder(getContentView(),
                PropertyValuesHolder.ofFloat("scaleX", 0f, 1f),
                PropertyValuesHolder.ofFloat("scaleY", 0f, 1f))

        scaleUp.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                mAnimationListener?.onAnimationEnd()
            }
        })

        scaleUp.duration = ANIMATION_DURATION
        fadeIn.duration = ANIMATION_DURATION
        scaleUp.start()
        fadeIn.start()
    }

    fun close() {
        val decorView = dialog.window?.decorView

        val fadeOut = ObjectAnimator.ofPropertyValuesHolder(decorView,
                PropertyValuesHolder.ofFloat("alpha", 1f, 0f))

        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(decorView,
                PropertyValuesHolder.ofFloat("scaleX", 1f, 0f),
                PropertyValuesHolder.ofFloat("scaleY", 1f, 0f))

        scaleDown.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                mAnimationListener?.onAnimationEnd()
            }
        })

        scaleDown.duration = ANIMATION_DURATION
        fadeOut.duration = ANIMATION_DURATION
        scaleDown.start()
        fadeOut.start()
    }

    /**
     * Return the Content View that needs to be scaled down
     */
    protected abstract fun getContentView(): View

    protected abstract fun getLayoutResId(): Int

    interface DialogAnimationListener {
        fun onAnimationEnd()
    }
}