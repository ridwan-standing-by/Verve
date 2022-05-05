package com.ridwanstandingby.verve.render

import android.graphics.Bitmap
import com.ridwanstandingby.verve.Api

@Api
class BitmapGenerator(private val x: Int, private val y: Int) {

    @Api
    val bitmap: Bitmap

    init {
        val config = Bitmap.Config.ARGB_8888
        bitmap = Bitmap.createBitmap(x, y, config)
    }

    @Api
    fun updateBitmap(pixelArray: IntArray) {
        bitmap.setPixels(pixelArray, 0, x, 0, 0, x, y)
    }
}
