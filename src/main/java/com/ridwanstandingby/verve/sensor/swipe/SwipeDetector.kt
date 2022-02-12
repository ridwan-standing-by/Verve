package com.ridwanstandingby.verve.sensor.swipe

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.VelocityTracker
import com.ridwanstandingby.verve.math.FloatVector2
import com.ridwanstandingby.verve.tools.Api
import java.util.concurrent.LinkedBlockingQueue

class SwipeDetector {

    @Api
    @Synchronized
    fun getSwipes(): List<Swipe> =
        mutableListOf<Swipe>().also { swipes.drainTo(it) }

    private val swipes: LinkedBlockingQueue<Swipe> = LinkedBlockingQueue()

    private var velocityTracker: VelocityTracker? = null

    @Synchronized
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
                swipes.add(
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
