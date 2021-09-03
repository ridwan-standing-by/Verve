package com.ridwanstandingby.verve.animation

import android.os.SystemClock
import com.ridwanstandingby.verve.tools.extractNsRemainderFromSToMs
import com.ridwanstandingby.verve.tools.nsToS
import com.ridwanstandingby.verve.tools.sToMs
import java.lang.Thread.sleep

internal class AnimationRunner(
    private val animationView: AnimationView,
    private val animation: Animation<*, *, *>
) {

    private var t = SystemClock.elapsedRealtimeNanos()
    private var lastUpdated = t
    private var lastRendered = t
    private var canDraw = false

    private var renderTaskThread: Thread? = null

    private val animationRunnable = Runnable {
        while (canDraw) {
            if (animationView.holder.surface.isValid) {
                continueRunning()
            }
        }
    }

    fun start() {
        canDraw = true
        renderTaskThread = Thread(animationRunnable, "Animation-Runner-Thread").also { it.start() }
    }

    fun stop() {
        canDraw = false
        while (renderTaskThread != null) {
            try {
                renderTaskThread?.join()
                renderTaskThread = null
                break
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun continueRunning() {
        t = SystemClock.elapsedRealtimeNanos()
        val dt = (t - lastUpdated).nsToS()
        if (dt < animation.parameters.minTimeStep) {
            val timeToSleep = animation.parameters.minTimeStep - dt
            sleep(timeToSleep.sToMs(), timeToSleep.extractNsRemainderFromSToMs())
        } else {
            update(dt)
            lastUpdated = t

            if ((t - lastRendered).nsToS() > 1 / animation.renderer.fps) {
                render()
                lastRendered = t
            }
        }
    }

    private fun update(dt: Double) {
        animation.update(dt)
    }

    private fun render() {
        val canvas = animationView.holder.lockCanvas()
        animation.renderer.updateCanvas(canvas)
        animationView.holder.unlockCanvasAndPost(canvas)
    }
}
