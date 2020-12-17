@file:Suppress("NOTHING_TO_INLINE", "unused", "MemberVisibilityCanBePrivate")

package com.ridwanstandingby.verve.math

import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.sqrt

data class Vector3(val x: Double, val y: Double, val z: Double) {

    operator fun plus(o: Vector3) = Vector3(x + o.x, y + o.y, z + o.z)
    operator fun minus(o: Vector3) = Vector3(x - o.x, y - o.y, z - o.z)
    operator fun times(k: Double) = Vector3(x * k, y * k, z * k)
    operator fun unaryMinus() = Vector3(-x, -y, -z)
    infix fun dot(o: Vector3) = x * o.x + y * o.y + z * o.z
    infix fun cross(o: Vector3) = Vector3(y * o.z - z * o.y, z * o.x - x * o.z, x * o.y - y * o.x)

    fun rotate(q: Quaternion): Vector3 = (q * (this.toQuaternion() * q.inverse())).axis()

    fun reflect(n: Vector3): Vector3 = this + n * ((this dot n) * -2.0)

    fun toSphericalVector2() =
        normalise().let { SphericalVector2(atan2(it.y, it.x), acos(it.z / it.size())) }

    private fun toQuaternion() = Quaternion(0.0, x, y, z)

    fun normalise() = size().let { norm ->
        Vector3(x / norm, y / norm, z / norm)
    }

    fun size() = sqrt(x * x + y * y + z * z)

    companion object {
        val O = Vector3(0.0, 0.0, 0.0)
    }
}

inline fun dist(a: Vector3, b: Vector3): Double =
    sqrt((a.x - b.x).sq() + (a.y - b.y).sq() + (a.z - b.z).sq())

inline fun dist2(a: Vector3, b: Vector3): Double =
    (a.x - b.x).sq() + (a.y - b.y).sq() + (a.z - b.z).sq()

