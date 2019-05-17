package com.ridwanstandingby.verve.math

import kotlin.math.cos
import kotlin.math.sin

data class Euler3(val phi: Double, val theta: Double, val psi: Double) {

    val sinTheta = sin(theta)
    val cosTheta = cos(theta)
    val sinPhi = sin(phi)
    val cosPhi = cos(phi)
    val sinPsi = sin(psi)
    val cosPsi = cos(psi)

    fun toQuaternion() = Quaternion(
        cos(phi / 2) * cos(theta / 2) * cos(psi / 2) + sin(phi / 2) * sin(theta / 2) * sin(psi / 2),
        sin(phi / 2) * cos(theta / 2) * cos(psi / 2) - cos(phi / 2) * sin(theta / 2) * sin(psi / 2),
        cos(phi / 2) * sin(theta / 2) * cos(psi / 2) + sin(phi / 2) * cos(theta / 2) * sin(psi / 2),
        cos(phi / 2) * cos(theta / 2) * sin(psi / 2) - sin(phi / 2) * sin(theta / 2) * cos(psi / 2)
    )
}
