package com.ridwanstandingby.verve.math

import com.ridwanstandingby.verve.tools.Api
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Api
data class Quaternion(val w: Double, val x: Double, val y: Double, val z: Double) {
    @Api
    constructor(angle: Double, axis: Vector3) : this(
        cos(angle / 2),
        sin(angle / 2) * axis.x,
        sin(angle / 2) * axis.y,
        sin(angle / 2) * axis.z
    )

    @Api
    operator fun plus(o: Quaternion) = Quaternion(w + o.w, x + o.x, y + o.y, z + o.z)

    @Api
    operator fun times(o: Quaternion) =
        Quaternion(
            w * o.w - x * o.x - y * o.y - z * o.z,
            w * o.x + x * o.w + y * o.z - z * o.y,
            w * o.y - x * o.z + y * o.w + z * o.x,
            w * o.z + x * o.y - y * o.x + z * o.w
        )

    @Api
    fun inverse() = Quaternion(w, -x, -y, -z)

    @Api
    fun axis() = Vector3(x, y, z)

    @Api
    fun toEuler3() = Euler3(
        atan2(2 * (w * x + y * z), 1 - 2 * (x * x + y * y)),
        asin(2 * (w * y - z * x)),
        atan2(2 * (w * z + x * y), 1 - 2 * (y * y + z * z))
    )
}
