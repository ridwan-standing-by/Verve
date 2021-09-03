@file:Suppress("NOTHING_TO_INLINE")

package com.ridwanstandingby.verve.math

import com.ridwanstandingby.verve.tools.Api

@Api
inline fun Float.sq() = this * this

@Api
inline fun Double.sq() = this * this

@Api
inline fun Double.cubed() = this * this * this
