package com.ridwanstandingby.verve.animation

import android.graphics.Canvas
import com.ridwanstandingby.verve.tools.Api

@Api
abstract class AnimationRenderer(open val fps: Float = 60f) {

    abstract fun updateCanvas(canvas: Canvas)
}
