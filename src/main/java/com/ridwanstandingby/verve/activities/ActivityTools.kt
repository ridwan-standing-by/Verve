package com.ridwanstandingby.verve.activities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import com.ridwanstandingby.verve.math.IntVector2
import com.ridwanstandingby.verve.sensor.RotationDetector

fun AnimationActivity.calculateScreenSize(): IntVector2 {
    val metrics = DisplayMetrics().also { windowManager.defaultDisplay.getRealMetrics(it) }
    return IntVector2(metrics.widthPixels, metrics.heightPixels)
}

fun AnimationActivity.createRotationDetector(): RotationDetector {
    val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    return RotationDetector(sensorManager, rotationSensor)
}

fun AnimationActivity.keepScreenOnAndHideSystemUI() {
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    supportActionBar?.hide()
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN)
}
