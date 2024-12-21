package com.ridwanstandingby.verve.activities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import com.ridwanstandingby.verve.math.IntVector2
import com.ridwanstandingby.verve.sensor.press.PressDetector
import com.ridwanstandingby.verve.sensor.rotation.RotationDetector
import com.ridwanstandingby.verve.sensor.swipe.SwipeDetector
import com.ridwanstandingby.verve.Api

@Api
fun AnimationActivity.calculateScreenSize(): IntVector2 {
    @Suppress("deprecation")
    val metrics = DisplayMetrics().also { windowManager.defaultDisplay.getRealMetrics(it) }
    return IntVector2(metrics.widthPixels, metrics.heightPixels)
}

@Api
fun AnimationActivity.createRotationDetector(): RotationDetector? {
    val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) ?: return null
    return RotationDetector(sensorManager, rotationSensor)
}

@Api
fun AnimationActivity.createSwipeDetector(): SwipeDetector =
    SwipeDetector().also { motionEventHandlers.add(it::handleMotionEvent) }

@Api
fun AnimationActivity.createPressDetector(): PressDetector =
    PressDetector().also { motionEventHandlers.add(it::handleMotionEvent) }

@Api
fun AnimationActivity.keepScreenOnAndHideSystemUI() {
    @Suppress("deprecation")
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_FULLSCREEN)
    supportActionBar?.hide()
    @Suppress("deprecation")
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN)
}
