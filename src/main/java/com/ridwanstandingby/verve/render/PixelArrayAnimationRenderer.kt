package com.ridwanstandingby.verve.render

import android.graphics.Canvas
import com.ridwanstandingby.verve.animation.AnimationRenderer
import com.ridwanstandingby.verve.Api

@Api
abstract class PixelArrayAnimationRenderer(@Api val worldX: Int, @Api val worldY: Int) :
    AnimationRenderer() {

    @Api
    val pixelArray: IntArray = IntArray(worldX * worldY)

    private val bitmapGenerator = BitmapGenerator(worldX, worldY)

    init {
        for (i in 0 until worldX * worldY) {
            pixelArray[i] = -0x1000000
        }
    }

    override fun updateCanvas(canvas: Canvas) {
        bitmapGenerator.updateBitmap(pixelArray)
        canvas.drawBitmap(bitmapGenerator.bitmap, 0f, 0f, null)
    }
}
