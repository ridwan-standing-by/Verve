package com.ridwanstandingby.verve.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ridwanstandingby.verve.animation.AnimationRenderView

abstract class AnimationActivity : AppCompatActivity() {

    private lateinit var animationRenderView: AnimationRenderView

    abstract fun createAnimationRenderView(): AnimationRenderView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animationRenderView = createAnimationRenderView()
        setContentView(animationRenderView)
    }

    override fun onResume() {
        super.onResume()
        keepScreenOnAndHideSystemUI()
        animationRenderView.resume()
    }

    override fun onPause() {
        super.onPause()
        animationRenderView.pause()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            keepScreenOnAndHideSystemUI()
        }
    }
}
