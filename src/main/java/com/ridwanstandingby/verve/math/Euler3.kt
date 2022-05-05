package com.ridwanstandingby.verve.math

import com.ridwanstandingby.verve.Api
import kotlin.math.cos
import kotlin.math.sin

@Api
data class Euler3(val phi: Double, val theta: Double, val psi: Double) {

    @Api
    val sinTheta = sin(theta)

    @Api
    val cosTheta = cos(theta)

    @Api
    val sinPhi = sin(phi)

    @Api
    val cosPhi = cos(phi)

    @Api
    val sinPsi = sin(psi)

    @Api
    val cosPsi = cos(psi)

    @Api
    fun toQuaternion() = Quaternion(
        cos(phi / 2) * cos(theta / 2) * cos(psi / 2) + sin(phi / 2) * sin(theta / 2) * sin(psi / 2),
        sin(phi / 2) * cos(theta / 2) * cos(psi / 2) - cos(phi / 2) * sin(theta / 2) * sin(psi / 2),
        cos(phi / 2) * sin(theta / 2) * cos(psi / 2) + sin(phi / 2) * cos(theta / 2) * sin(psi / 2),
        cos(phi / 2) * cos(theta / 2) * sin(psi / 2) - sin(phi / 2) * sin(theta / 2) * cos(psi / 2)
    )
}
