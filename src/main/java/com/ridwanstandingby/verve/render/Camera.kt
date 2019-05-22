package com.ridwanstandingby.verve.render

import com.ridwanstandingby.verve.math.FloatVector2
import com.ridwanstandingby.verve.math.IntVector2
import com.ridwanstandingby.verve.math.Quaternion
import com.ridwanstandingby.verve.math.Vector3
import com.ridwanstandingby.verve.render.Camera.Companion.CAMERA_FRAME_FORWARD

/**
 * The [Camera] is fully defined by a quaternion stored in [transformation] that encodes how to transform between the
 * world's frame and the camera's frame. The screen is a plane defined by [CAMERA_FRAME_FORWARD] that points can be
 * projected to and from.
 */
class Camera(
    val screenDimension: IntVector2,
    private val rotationTransformer: (Quaternion) -> Quaternion,
    initialDirection: Quaternion = CAMERA_FRAME_IDENTITY_TRANSFORM
) {

    private val screenOrigin = FloatVector2(screenDimension.x / 2, screenDimension.y / 2)

    var transformation: Quaternion = initialDirection
        private set(value) {
            field = value
            direction = inverseTransform(CAMERA_FRAME_FORWARD)
            rightDirection = inverseTransform(CAMERA_FRAME_RIGHT)
            upDirection = inverseTransform(CAMERA_FRAME_UP)
            leftDirection = -rightDirection
            downDirection = -upDirection
        }

    var direction: Vector3 = CAMERA_FRAME_FORWARD
        private set
    var rightDirection: Vector3 = CAMERA_FRAME_RIGHT
        private set
    var leftDirection: Vector3 = -CAMERA_FRAME_RIGHT
        private set
    var upDirection: Vector3 = CAMERA_FRAME_UP
        private set
    var downDirection: Vector3 = -CAMERA_FRAME_UP
        private set

    fun updateCamera(q: Quaternion) {
        transformation = rotationTransformer(q)
    }

    /**
     * Transform a vector from the world's frame to the camera's frame
     */
    fun transform(v: Vector3) =
        v.rotate(transformation.inverse()) // The inverse is used to rotate everything else in the view to the Camera's frame

    /**
     * Transform a vector from the camera's frame to the world's frame
     */
    fun inverseTransform(v: Vector3) =
        v.rotate(transformation)

    /**
     * Transform a vector from the world's frame to the screen's pixels
     */
    fun project(v: Vector3): FloatVector2 =
        transform(v).let {
            FloatVector2(it.x, -it.y) + screenOrigin // Note that camY is measured from the top of the screen
        }

    /**
     * Transform from screen's pixels to a vector in the world's frame
     */
    fun inverseProject(v: FloatVector2) =
        inverseTransform((v - screenOrigin).let {
            Vector3(it.x.toDouble(), -it.y.toDouble(), 0.0)
        })

    companion object {
        // The camera is looking down the z axis towards minus infinity in the camera frame
        private val CAMERA_FRAME_FORWARD = Vector3(0.0, 0.0, -1.0)
        private val CAMERA_FRAME_RIGHT = Vector3(1.0, 0.0, 0.0)
        private val CAMERA_FRAME_UP = Vector3(0.0, 1.0, 0.0)
        private val CAMERA_FRAME_IDENTITY_TRANSFORM = Quaternion(0.0, CAMERA_FRAME_FORWARD)
    }
}
