package com.ridwanstandingby.verve.math

import com.ridwanstandingby.verve.tools.Api
import kotlin.math.sqrt

@Api
data class FloatVector2(val x: Float, val y: Float) {
    @Api
    constructor(x: Double, y: Double) : this(x.toFloat(), y.toFloat())
    @Api
    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())

    @Api
    operator fun plus(o: FloatVector2) = FloatVector2(x + o.x, y + o.y)

    @Api
    operator fun minus(o: FloatVector2) = FloatVector2(x - o.x, y - o.y)

    companion object {
        @Api
        val O = FloatVector2(0f, 0f)
    }
}

@Api
fun dist(a: FloatVector2, b: FloatVector2) = sqrt((a.x - b.x).sq() + (a.y - b.y).sq())
