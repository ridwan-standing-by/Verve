package com.ridwanstandingby.verve.activities

import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.ridwanstandingby.verve.animation.AnimationView
import com.ridwanstandingby.verve.math.IntVector2
import com.ridwanstandingby.verve.tools.Api

@Api
abstract class AnimationActivity : AppCompatActivity() {

    private var animationView: AnimationView? = null

    protected abstract fun defineAnimationView(viewSize: IntVector2): AnimationView

    @Api
    fun createAnimationView(viewSize: IntVector2 = calculateScreenSize()): AnimationView =
        defineAnimationView(viewSize)
            .also { animationView = it }
            .also { if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) it.resume() }

    override fun onResume() {
        super.onResume()
        keepScreenOnAndHideSystemUI()
        animationView?.resume()
    }

    override fun onPause() {
        super.onPause()
        animationView?.pause()
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
