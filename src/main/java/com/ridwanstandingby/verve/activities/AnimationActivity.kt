package com.ridwanstandingby.verve.activities

import android.view.MotionEvent
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import com.ridwanstandingby.verve.animation.AnimationRunner
import com.ridwanstandingby.verve.tools.Api

@Api
abstract class AnimationActivity : AppCompatActivity() {

    protected abstract fun getAnimationRunner(): AnimationRunner

    @Api
    fun createAndAttachAnimationView(): SurfaceView =
        SurfaceView(this)
            .also { attachAnimationSurfaceToRunner(it) }

    private fun attachAnimationSurfaceToRunner(animationSurface: SurfaceView) {
        getAnimationRunner().attach(animationSurface)
    }

    override fun onResume() {
        super.onResume()
        keepScreenOnAndHideSystemUI()
        getAnimationRunner().resume()
    }

    override fun onPause() {
        super.onPause()
        getAnimationRunner().pause()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            keepScreenOnAndHideSystemUI()
        }
    }

    val motionEventHandlers: MutableList<(MotionEvent) -> Unit> = mutableListOf()

    @Api
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            if (motionEventHandlers.isEmpty()) return super.onTouchEvent(event)
            this.motionEventHandlers.forEach { it.invoke(event) }
            return true
        }
        return false
    }
}
