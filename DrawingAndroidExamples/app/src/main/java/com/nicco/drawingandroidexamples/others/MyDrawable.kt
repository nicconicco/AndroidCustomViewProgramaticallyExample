package com.nicco.drawingandroidexamples.others

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

class MyDrawable : Drawable() {
        private val redPaint: Paint = Paint().apply { setARGB(255, 255, 0, 0) }

        override fun draw(canvas: Canvas) {
            // Get the drawable's bounds
            val width: Int = bounds.width()
            val height: Int = bounds.height()
            val radius: Float = width.coerceAtMost(height).toFloat() / 2f

            // Draw a red circle in the center
            canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, redPaint)
        }

        override fun setAlpha(alpha: Int) {
            // This method is required
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
            // This method is required
        }

        override fun getOpacity(): Int =
            // Must be PixelFormat.UNKNOWN, TRANSLUCENT, TRANSPARENT, or OPAQUE
            PixelFormat.OPAQUE

    }