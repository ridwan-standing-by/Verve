@file:Suppress("NOTHING_TO_INLINE")

package com.ridwanstandingby.verve.math

import com.ridwanstandingby.verve.Api
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.sqrt

@Api
data class Vector3(val x: Double, val y: Double, val z: Double) {

    @Api
    operator fun plus(o: Vector3) = Vector3(x + o.x, y + o.y, z + o.z)

    @Api
    operator fun minus(o: Vector3) = Vector3(x - o.x, y - o.y, z - o.z)

    @Api
    operator fun times(k: Double) = Vector3(x * k, y * k, z * k)

    @Api
    operator fun unaryMinus() = Vector3(-x, -y, -z)

    @Api
    infix fun dot(o: Vector3) = x * o.x + y * o.y + z * o.z

    @Api
    infix fun cross(o: Vector3) = Vector3(y * o.z - z * o.y, z * o.x - x * o.z, x * o.y - y * o.x)

    @Api
    fun rotate(q: Quaternion): Vector3 = (q * (this.toQuaternion() * q.inverse())).axis()

    @Api
    fun reflect(n: Vector3): Vector3 = this + n * ((this dot n) * -2.0)

    @Api
    fun toSphericalVector2() =
        normalise().let { SphericalVector2(atan2(it.y, it.x), acos(it.z / it.size())) }

    @Api
    fun toQuaternion() = Quaternion(0.0, x, y, z)

    @Api
    fun normalise() = size().let { norm ->
        Vector3(x / norm, y / norm, z / norm)
    }

    @Api
    fun size() = sqrt(x * x + y * y + z * z)

    companion object {
        @Api
        val O = Vector3(0.0, 0.0, 0.0)
    }
}

@Api
inline fun dist(a: Vector3, b: Vector3): Double =
    sqrt((a.x - b.x).sq() + (a.y - b.y).sq() + (a.z - b.z).sq())

@Api
inline fun dist2(a: Vector3, b: Vector3): Double =
    (a.x - b.x).sq() + (a.y - b.y).sq() + (a.z - b.z).sq()
