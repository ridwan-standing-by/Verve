package com.ridwanstandingby.verve.math

import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

data class Quaternion(val w: Double, val x: Double, val y: Double, val z: Double) {
    constructor(angle: Double, axis: Vector3) : this(
        cos(angle / 2),
        sin(angle / 2) * axis.x,
        sin(angle / 2) * axis.y,
        sin(angle / 2) * axis.z
    )

    operator fun plus(o: Quaternion) = Quaternion(w + o.w, x + o.x, y + o.y, z + o.z)

    operator fun times(o: Quaternion) =
        Quaternion(
            w * o.w - x * o.x - y * o.y - z * o.z,
            w * o.x + x * o.w + y * o.z - z * o.y,
            w * o.y - x * o.z + y * o.w + z * o.x,
            w * o.z + x * o.y - y * o.x + z * o.w
        )

    fun inverse() = Quaternion(w, -x, -y, -z)

    fun axis() = Vector3(x, y, z)

    fun toEuler3() = Euler3(
        atan2(2 * (w * x + y * z), 1 - 2 * (x * x + y * y)),
        asin(2 * (w * y - z * x)),
        atan2(2 * (w * z + x * y), 1 - 2 * (y * y + z * z))
    )
}
