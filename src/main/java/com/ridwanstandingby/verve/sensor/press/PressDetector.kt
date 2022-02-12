package com.ridwanstandingby.verve.sensor.press

import android.view.MotionEvent
import com.ridwanstandingby.verve.math.FloatVector2
import com.ridwanstandingby.verve.tools.Api

class PressDetector {

    private val presses = mutableListOf<Press>()

    @Api
    @Synchronized
    fun updateAndGetPresses(dt: Double): List<Press> =
        presses.map { it.also { it.runningTime += dt }.copy() }

    @Api
    @Synchronized
    fun resetPresses() {
        presses.forEach { it.runningTime = 0.0 }
    }

    @Synchronized
    fun handleMotionEvent(event: MotionEvent) {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN ->
                createPress(event)
            MotionEvent.ACTION_MOVE ->
                resetPress(event)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL ->
                clearPress(event)
        }
    }

    private fun createPress(event: MotionEvent) {
        val actionIndex = event.actionIndex
        val id = event.getPointerId(actionIndex)
        if (presses.none { it.id == id }) {
            presses.add(
                Press(
                    id = id,
                    screenPosition = FloatVector2(event.getX(actionIndex), event.getY(actionIndex)),
                    runningTime = 0.0
                )
            )
        }
    }

    private fun resetPress(event: MotionEvent) {
        val actionIndex = event.actionIndex
        val id = event.getPointerId(actionIndex)
        presses.filter { it.id == id }.forEach { it.runningTime = 0.0 }
    }

    private fun clearPress(event: MotionEvent) {
        val actionIndex = event.actionIndex
        val id = event.getPointerId(actionIndex)
        presses.removeAll { it.id == id }
    }
}
