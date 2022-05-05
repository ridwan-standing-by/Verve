package com.ridwanstandingby.verve.sensor.press

import com.ridwanstandingby.verve.math.FloatVector2
import com.ridwanstandingby.verve.Api

@Api
data class Press(
    val id: Int,
    val screenPosition: FloatVector2,
    var runningTime: Double // seconds
)
