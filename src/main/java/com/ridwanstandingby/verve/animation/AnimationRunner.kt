package com.ridwanstandingby.verve.animation

import android.os.SystemClock
import android.view.View
import com.ridwanstandingby.verve.tools.Api
import com.ridwanstandingby.verve.tools.extractNsRemainderFromSToMs
import com.ridwanstandingby.verve.tools.nsToS
import com.ridwanstandingby.verve.tools.sToMs
import java.lang.Thread.sleep
import kotlin.math.min

@Api
class AnimationRunner(
    var animation: Animation<*, *, *>? = null
) {
    private var t = SystemClock.elapsedRealtimeNanos()
    private var lastUpdated = t
    private var lastRendered = t
    private var canDraw = false
    private var paused = false

    private var animationView: AnimationView? = null

    private var renderTaskThread: Thread? = null

    private val animationRunnable = Runnable {
        while (canDraw) {
            if (!paused) {
                continueRunning()
            }
        }
    }

    fun attach(animationView: AnimationView) {
        animationView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View?) {
                this@AnimationRunner.animationView = animationView
                resume()
            }

            override fun onViewDetachedFromWindow(v: View?) {
                pause()
                this@AnimationRunner.animationView = null
            }
        })
    }

    fun start(animation: Animation<*, *, *>?) {
        this.animation = animation
        canDraw = true
        renderTaskThread = Thread(animationRunnable, "Animation-Runner-Thread").also { it.start() }
    }

    fun resume() {
        t = SystemClock.elapsedRealtimeNanos()
        lastUpdated = t
        lastRendered = t
        paused = false
    }

    fun pause() {
        paused = true
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
        animation?.let {
            t = SystemClock.elapsedRealtimeNanos()
            val dt = (t - lastUpdated).nsToS()
            if (dt < it.parameters.minTimeStep) {
                val timeToSleep = it.parameters.minTimeStep - dt
                sleep(timeToSleep.sToMs(), timeToSleep.extractNsRemainderFromSToMs())
            } else {
                update(min(dt, it.parameters.maxTimeStep))
                lastUpdated = t

                if ((t - lastRendered).nsToS() > 1 / it.renderer.fps) {
                    render()
                    lastRendered = t
                }
            }
        }
    }

    private fun update(dt: Double) {
        animation?.update(dt)
    }

    private fun render() {
        animationView?.let {
            if (it.holder.surface.isValid) {
                val canvas = it.holder.lockCanvas()
                if (canvas != null) {
                    animation?.renderer?.updateCanvas(canvas)
                    it.holder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }
}
