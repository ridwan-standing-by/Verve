package com.ridwanstandingby.verve.sensor.swipe

import com.ridwanstandingby.verve.math.FloatVector2
import com.ridwanstandingby.verve.tools.Api

@Api
data class Swipe(val screenPosition: FloatVector2, val screenVelocity: FloatVector2)
