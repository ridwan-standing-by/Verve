package com.ridwanstandingby.verve.sensor.swipe

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.VelocityTracker
import com.ridwanstandingby.verve.math.FloatVector2

class SwipeDetector {

    var swipes = mutableListOf<Swipe>()

    private var mVelocityTracker: VelocityTracker? = null

    @SuppressLint("Recycle")
    fun handleMotionEvent(event: MotionEvent) {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mVelocityTracker?.clear()
                mVelocityTracker = mVelocityTracker ?: VelocityTracker.obtain()
                mVelocityTracker?.addMovement(event)
            }
            MotionEvent.ACTION_MOVE -> {
                mVelocityTracker?.apply {
                    val pointerId: Int = event.getPointerId(event.actionIndex)
                    addMovement(event)
                    computeCurrentVelocity(1000)
                    swipes.add(
                        Swipe(
                            FloatVector2(event.getX(pointerId), event.getY(pointerId)),
                            FloatVector2(getXVelocity(pointerId), getYVelocity(pointerId))
                        )
                    )
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mVelocityTracker?.recycle()
                mVelocityTracker = null
            }
        }
    }
}
