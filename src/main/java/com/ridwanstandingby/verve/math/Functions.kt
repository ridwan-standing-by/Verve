@file:Suppress("NOTHING_TO_INLINE")

package com.ridwanstandingby.verve.math

import com.ridwanstandingby.verve.tools.Api
import kotlin.math.abs

@Api
inline fun Float.sq() = this * this

@Api
inline fun Double.sq() = this * this

@Api
inline fun Double.cubed() = this * this * this

/** Calculates the smallest [a] - [b] if both belong on a toroidal field with [period] */
@Api
inline fun toroidalDiff(a: Double, b: Double, period: Double): Double {
    val ba = a - b
    val baAlt = if (ba > 0) {
        ba - period
    } else {
        ba + period
    }
    return if (abs(ba) > abs(baAlt)) {
        baAlt
    } else {
        ba
    }
}
