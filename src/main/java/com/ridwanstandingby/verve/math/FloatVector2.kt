package com.ridwanstandingby.verve.math

data class FloatVector2(val x: Float, val y: Float){
    constructor(x: Double, y: Double) : this(x.toFloat(), y.toFloat())
    constructor(x: Int, y: Int): this(x.toFloat(), y.toFloat())

    operator fun plus(o: FloatVector2) = FloatVector2(x + o.x, y + o.y)
}
