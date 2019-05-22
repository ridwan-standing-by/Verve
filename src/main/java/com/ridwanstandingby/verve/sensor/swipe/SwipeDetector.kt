package com.ridwanstandingby.verve.sensor.swipe

import android.annotation.SuppressLint
import android.view.MotionEvent
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
            MotionEvent.ACTION_DOWN -> beginVelocityTracker(event)
            MotionEvent.ACTION_MOVE -> createSwipe(event)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> resetVelocityTracker()
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
            val pointerId: Int = event.getPointerId(event.actionIndex)
            addMovement(event)
            computeCurrentVelocity(1000)
            swipesBuffer.add(
                Swipe(
                    FloatVector2(event.getX(pointerId), event.getY(pointerId)),
                    FloatVector2(getXVelocity(pointerId), getYVelocity(pointerId))
                )
            )
        }
    }

    private fun resetVelocityTracker() {
        velocityTracker?.recycle()
        velocityTracker = null
    }
}
