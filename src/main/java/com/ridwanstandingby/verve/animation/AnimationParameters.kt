package com.ridwanstandingby.verve.animation

import com.ridwanstandingby.verve.Api

@Api
abstract class AnimationParameters(
    open val minTimeStep: Double = 0.001,
    open val maxTimeStep: Double = 0.03
)
