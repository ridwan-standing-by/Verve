package com.ridwanstandingby.verve.render

import com.ridwanstandingby.verve.math.FloatVector2
import com.ridwanstandingby.verve.math.IntVector2
import com.ridwanstandingby.verve.math.Quaternion
import com.ridwanstandingby.verve.math.Vector3

class Camera(
    val screenDimension: IntVector2,
    private val rotationTransformer: (Quaternion) -> Quaternion,
    initialDirection: Quaternion = CAMERA_FRAME_IDENTITY_TRANSFORM
) {

    private val screenOrigin = FloatVector2(screenDimension.x / 2, screenDimension.y / 2)

    private var direction: Quaternion = initialDirection
        set(value) {
            field = value
            rightDirection = CAMERA_FRAME_RIGHT.rotate(direction)
            leftDirection = rightDirection * -1.0
            upDirection = CAMERA_FRAME_UP.rotate(direction)
            downDirection = upDirection * -1.0
        }

    var rightDirection: Vector3 = CAMERA_FRAME_RIGHT
        private set
    var leftDirection: Vector3 = CAMERA_FRAME_RIGHT * -1.0
        private set
    var upDirection: Vector3 = CAMERA_FRAME_UP
        private set
    var downDirection: Vector3 = CAMERA_FRAME_UP * -1.0
        private set

    fun transform(q: Quaternion) {
        direction = rotationTransformer(q)
    }

    fun project(u: Vector3): FloatVector2 =
        u.rotate(direction.inverse()) // The inverse is used to rotate everything else in the view to the Camera's frame
            .let { v ->
                FloatVector2(v.x, -v.y) + screenOrigin // Note that camY is measured from the top of the screen
            }

    companion object {
        // The camera is looking down the z axis towards minus infinity in the camera frame
        private val CAMERA_FRAME_FORWARD = Vector3(0.0, 0.0, -1.0)
        private val CAMERA_FRAME_RIGHT = Vector3(1.0, 0.0, 0.0)
        private val CAMERA_FRAME_UP = Vector3(0.0, 1.0, 0.0)
        private val CAMERA_FRAME_IDENTITY_TRANSFORM = Quaternion(0.0, CAMERA_FRAME_FORWARD)
    }
}
