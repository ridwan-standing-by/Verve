package com.ridwanstandingby.verve.math

import kotlin.math.sqrt

data class FloatVector2(val x: Float, val y: Float) {
    constructor(x: Double, y: Double) : this(x.toFloat(), y.toFloat())
    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())

    operator fun plus(o: FloatVector2) = FloatVector2(x + o.x, y + o.y)
    operator fun minus(o: FloatVector2) = FloatVector2(x - o.x, y - o.y)
}

fun dist(a: FloatVector2, b: FloatVector2) = sqrt((a.x - b.x).sq() + (a.y - b.y).sq())
