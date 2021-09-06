package com.ridwanstandingby.verve.activities

import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.ridwanstandingby.verve.animation.AnimationRunner
import com.ridwanstandingby.verve.animation.AnimationView
import com.ridwanstandingby.verve.math.IntVector2
import com.ridwanstandingby.verve.tools.Api

@Api
abstract class AnimationActivity : AppCompatActivity() {

    protected abstract fun getAnimationRunner(): AnimationRunner

    @Api
    fun createAndAttachAnimationView(viewSize: IntVector2 = calculateScreenSize()): AnimationView =
        AnimationView(this)
            .also { attachAnimationViewToRunner(it) }

    private fun attachAnimationViewToRunner(animationView: AnimationView) {
        getAnimationRunner().attach(animationView)
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

    var motionEventHandler: ((MotionEvent) -> Unit)? = null
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            this.motionEventHandler?.invoke(event) ?: return super.onTouchEvent(event)
            return true
        }
        return false
    }
}
