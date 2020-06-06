package aso.mo.asoplayer.main.common.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import aso.mo.asoplayer.databinding.FragmentPlaybackContentBinding
import kotlin.math.abs


class PlayerLayoutOverlay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    val binding = FragmentPlaybackContentBinding.inflate(LayoutInflater.from(context), this, false)

    private var motionLayout: MultiListenerMotionLayout = binding.root as MultiListenerMotionLayout

    private val touchableArea: View

    init {
        addView(motionLayout)

        touchableArea = binding.videoOverlayTouchableArea
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val isInProgress = (motionLayout.progress > 0.0f && motionLayout.progress < 1.0f)
        val isInTarget = touchEventInsideTargetView(touchableArea, ev)

        return if (isInProgress || isInTarget) {
            super.onInterceptTouchEvent(ev)
        } else {
            true
        }
    }

    private fun touchEventInsideTargetView(v: View, ev: MotionEvent): Boolean {
        if (ev.x > v.left && ev.x < v.right) {
            if (ev.y > v.top && ev.y < v.bottom) {
                return true
            }
        }
        return false
    }

    private fun doClickTransition(): Boolean {

        return false
    }

    private fun isAClick(
        startX: Float = 0f,
        endX: Float,
        startY: Float = 0f,
        endY: Float
    ): Boolean {
        val differenceX = abs(startX - endX)
        val differenceY = abs(startY - endY)
        return !/* =5 */(differenceX > 200 || differenceY > 200)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }

}