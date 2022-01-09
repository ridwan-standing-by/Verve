package com.ridwanstandingby.verve.sensor.press

import android.view.MotionEvent
import com.ridwanstandingby.verve.math.FloatVector2
import com.ridwanstandingby.verve.tools.Api

class PressDetector {

    @Api
    var presses = mutableListOf<Press>()

    fun handleMotionEvent(event: MotionEvent) {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN ->
                createPress(event)
            MotionEvent.ACTION_MOVE ->
                resetPress(event)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
                clearPress(event)
        }
    }

    private fun createPress(event: MotionEvent) {
        (0 until event.pointerCount).forEach { pointerIndex ->
            val id = event.getPointerId(pointerIndex)
            presses.add(
                Press(
                    id = id,
                    screenPosition = FloatVector2(
                        event.getX(pointerIndex),
                        event.getY(pointerIndex)
                    ),
                    runningTime = 0.0
                )
            )
        }
    }

    private fun resetPress(event: MotionEvent) {
        (0 until event.pointerCount).forEach { pointerIndex ->
            val id = event.getPointerId(pointerIndex)
            presses.filter { it.id == id }.forEach { it.runningTime = 0.0 }
        }
    }

    private fun clearPress(event: MotionEvent) {
        (0 until event.pointerCount).forEach { pointerIndex ->
            val id = event.getPointerId(pointerIndex)
            presses.removeAll { it.id == id }
        }
    }

    @Api
    fun updatePresses(dt: Double) {
        presses.forEach { it.runningTime += dt }
    }
}
