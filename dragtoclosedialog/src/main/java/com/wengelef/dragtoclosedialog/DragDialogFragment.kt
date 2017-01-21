package com.wengelef.dragtoclosedialog

import android.support.v4.view.MotionEventCompat
import android.view.MotionEvent
import android.view.View

abstract class DragDialogFragment : BaseDialogFragment() {

    enum class CloseDirection { DEFAULT, BOTTOM, TOP }

    private val DRAG_TO_DISMISS_DISTANCE = 400

    private var initialDragContainerPosition = 0f

    override fun onStart() {
        mAnimationListener = getDialogAnimationListener()
        getContentView().setOnTouchListener(getTouchListener())
        super.onStart()
    }

    protected open fun getCloseDirection(): CloseDirection = CloseDirection.DEFAULT

    private fun getDialogAnimationListener(): DialogAnimationListener = object : DialogAnimationListener {
        override fun onAnimationEnd() {
            initialDragContainerPosition = getContentView().y
        }
    }

    private fun getTouchListener(): View.OnTouchListener = object : View.OnTouchListener {
        private var lastTouchY: Float = 0f
        private var distanceCovered: Float = 0f

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            val action = MotionEventCompat.getActionMasked(event)

            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    lastTouchY = event.rawY
                    distanceCovered = 0f
                }

                MotionEvent.ACTION_MOVE -> {
                    val y = event.rawY
                    val dy = y - lastTouchY
                    distanceCovered += dy
                    getContentView().y = getContentView().y + dy
                    lastTouchY = y
                }

                MotionEvent.ACTION_UP -> onDragEnd()

                MotionEvent.ACTION_CANCEL -> onDragEnd()

                else -> return true
            }
            return true
        }

        private fun onDragEnd() {
            if (getCloseableBottom()) {
                if (distanceCovered > DRAG_TO_DISMISS_DISTANCE) {
                    close()
                } else {
                    getContentView().animate().y(initialDragContainerPosition)
                }
            }
            if (getCloseableTop()) {
                if (distanceCovered < (DRAG_TO_DISMISS_DISTANCE * -1)) {
                    close()
                } else {
                    getContentView().animate().y(initialDragContainerPosition)
                }
            }
        }
    }

    private fun getCloseableBottom(): Boolean {
        return getCloseDirection() == CloseDirection.DEFAULT || getCloseDirection() == CloseDirection.BOTTOM
    }

    private fun getCloseableTop(): Boolean {
        return getCloseDirection() == CloseDirection.DEFAULT || getCloseDirection() == CloseDirection.TOP
    }
}