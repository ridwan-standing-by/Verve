package com.ridwanstandingby.verve.animation

import android.os.SystemClock
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.ridwanstandingby.verve.Api
import com.ridwanstandingby.verve.tools.extractNsRemainderFromSToMs
import com.ridwanstandingby.verve.tools.nsToS
import com.ridwanstandingby.verve.tools.sToMs
import java.lang.Thread.sleep
import kotlin.math.min

@Api
class AnimationRunner(
    @Api var animation: Animation<*, *, *>? = null
) {
    private var t = SystemClock.elapsedRealtimeNanos()
    private var lastUpdated = t
    private var lastRendered = t
    private var canDraw = false
    private var paused = false

    private var animationSurfaceHolder: SurfaceHolder? = null

    private var renderTaskThread: Thread? = null

    private val animationRunnable = Runnable {
        while (canDraw) {
            if (!paused) {
                continueRunning()
            } else {
                sleep(100)
            }
        }
    }

    private var animationViewOnAttachStateChangeListener: View.OnAttachStateChangeListener? = null

    @Api
    fun attach(animationView: SurfaceView) {
        animationView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View?) {
                this@AnimationRunner.animationSurfaceHolder = animationView.holder
                resume()
            }

            override fun onViewDetachedFromWindow(v: View?) {
                pause()
                this@AnimationRunner.animationSurfaceHolder = null
            }
        }.also { animationViewOnAttachStateChangeListener = it })
    }

    @Api
    fun detach(animationView: SurfaceView) {
        animationViewOnAttachStateChangeListener?.let {
            animationView.removeOnAttachStateChangeListener(it)
        }
    }

    @Api
    fun attachSurfaceHolder(animationSurfaceHolder: SurfaceHolder) {
        this.animationSurfaceHolder = animationSurfaceHolder
    }

    @Api
    fun detachSurfaceHolder() {
        this.animationSurfaceHolder = null
    }

    @Api
    fun start(animation: Animation<*, *, *>?) {
        this.animation = animation
        canDraw = true
        renderTaskThread = Thread(animationRunnable, "Animation-Runner-Thread").also { it.start() }
    }

    @Api
    fun resume() {
        t = SystemClock.elapsedRealtimeNanos()
        lastUpdated = t
        lastRendered = t
        paused = false
    }

    @Api
    fun pause() {
        paused = true
    }

    @Api
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
        animationSurfaceHolder?.let {
            if (it.surface.isValid) {
                val canvas = it.lockCanvas()
                if (canvas != null && it.surface?.isValid == true) {
                    animation?.renderer?.updateCanvas(canvas)
                    it.unlockCanvasAndPost(canvas)
                }
            }
        }
    }
}
