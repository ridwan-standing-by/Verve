package com.ridwanstandingby.verve.sensor.swipe

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.VelocityTracker
import com.ridwanstandingby.verve.math.FloatVector2

class SwipeDetector {

    var swipes = listOf<Swipe>()
        get() {
            field = swipesBuffer.toList()
            swipesBuffer = mutableListOf()
            return field
        }
        private set

    private var swipesBuffer = mutableListOf<Swipe>()

    private var velocityTracker: VelocityTracker? = null

    fun handleMotionEvent(event: MotionEvent) {
        when (event.actionMasked) {
            ACTION_DOWN -> beginVelocityTracker(event)
            ACTION_MOVE -> createSwipe(event)
            ACTION_UP, ACTION_CANCEL -> resetVelocityTracker()
        }
    }

    @SuppressLint("Recycle")
    private fun beginVelocityTracker(event: MotionEvent) {
        velocityTracker?.clear()
        velocityTracker = velocityTracker ?: VelocityTracker.obtain()
        velocityTracker?.addMovement(event)
    }

    private fun createSwipe(event: MotionEvent) {
        velocityTracker?.apply {
            addMovement(event)
            computeCurrentVelocity(1000)
            (0 until event.pointerCount).forEach { pointerIndex ->
                swipesBuffer.add(
                    Swipe(
                        FloatVector2(event.getX(pointerIndex), event.getY(pointerIndex)),
                        FloatVector2(getXVelocity(pointerIndex), getYVelocity(pointerIndex))
                    )
                )
            }
        }
    }

    private fun resetVelocityTracker() {
        velocityTracker?.recycle()
        velocityTracker = null
    }
}
