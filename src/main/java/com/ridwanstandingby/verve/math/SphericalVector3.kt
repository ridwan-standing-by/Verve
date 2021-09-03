package com.ridwanstandingby.verve.math

import com.ridwanstandingby.verve.tools.Api
import kotlin.math.cos
import kotlin.math.sin

@Api
data class SphericalVector3(val r: Double, val phi: Double, val theta: Double) {

    @Api
    fun resolve() = Vector3(r * sin(theta) * cos(phi), r * sin(theta) * sin(phi), r * cos(theta))
}
