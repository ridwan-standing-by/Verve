package com.ridwanstandingby.verve.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.ridwanstandingby.verve.math.Quaternion

class RotationDetector(
    private val sensorManager: SensorManager,
    private val rotationSensor: Sensor
) {

    var lastKnownOrientation = Quaternion(0.0, 1.0, 0.0, 0.0)

    fun start(pollingIntervalMicros: Int) {
        sensorManager.registerListener(object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.values?.let {

                    val q = FloatArray(4)
                    SensorManager.getQuaternionFromVector(q, it)

                    lastKnownOrientation =
                        Quaternion(it[0].toDouble(), it[1].toDouble(), it[2].toDouble(), it[3].toDouble())
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }
        }, rotationSensor, pollingIntervalMicros)
    }
}
