package com.ridwanstandingby.verve.math

import com.ridwanstandingby.verve.tools.Api
import kotlin.math.cos
import kotlin.math.sin

@Api
data class SphericalVector2(val phi: Double, val theta: Double) {

    @Api
    val sinTheta = sin(theta)

    @Api
    val cosTheta = cos(theta)

    @Api
    val sinPhi = sin(phi)

    @Api
    val cosPhi = cos(phi)

    @Api
    fun resolve() = Vector3(sinTheta * cosPhi, sinTheta * sinPhi, cosTheta)

    @Api
    operator fun times(k: Double) = SphericalVector3(k, phi, theta)
}
