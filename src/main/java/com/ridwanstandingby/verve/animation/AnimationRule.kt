package com.ridwanstandingby.verve.animation

import com.ridwanstandingby.verve.tools.Api

@Api
class AnimationRule<A : Animation<P, R, I>, P : AnimationParameters, R : AnimationRenderer, I : AnimationInput>(
    private val animationConstructor: (P, R, I) -> A,
    private val animationParameters: P,
    private val animationRenderer: R,
    private val animationInput: I
) {

    internal fun create(): A =
        animationConstructor(animationParameters, animationRenderer, animationInput)
}
