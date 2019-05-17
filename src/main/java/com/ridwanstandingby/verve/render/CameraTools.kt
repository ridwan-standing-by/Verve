package com.ridwanstandingby.verve.render

import com.ridwanstandingby.verve.math.Quaternion
import com.ridwanstandingby.verve.math.Vector3

val rotationSensorOutputToOuterSphericalCameraDirection = { q: Quaternion ->
    Quaternion(Math.PI, Vector3(0.0, 0.0, 1.0)) *
            Quaternion(-Math.PI / 2, Vector3(0.0, 1.0, 0.0)) *
            Quaternion(q.y, -q.z, -q.w, -q.x) *
            Quaternion(Math.PI / 2, Vector3(0.0, 1.0, 0.0))
}
