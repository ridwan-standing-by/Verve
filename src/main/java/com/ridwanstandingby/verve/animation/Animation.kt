package com.ridwanstandingby.verve.animation

import com.ridwanstandingby.verve.tools.Api

@Api
abstract class Animation<P : AnimationParameters, R : AnimationRenderer, I : AnimationInput>(
    @Api val parameters: P,
    @Api val renderer: R,
    @Api val input: I
) {

    abstract fun update(dt: Double)
}
