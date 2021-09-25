package com.nicco.drawingandroidexamples.others

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View


class DrawView(context: Context) : View(context) {

    private val TAG = "CirclesDrawingView"

    /** Main bitmap  */
    private val mBitmap: Bitmap? = null

    private val mMeasuredRect: Rect? = null

    /** Stores data about single circle  */
    private class CircleArea internal constructor(
        var centerX: Int,
        var centerY: Int,
        var radius: Int
    ) {
        override fun toString(): String {
            return "Circle[$centerX, $centerY, $radius]"
        }
    }

    var paint: Paint = Paint()

    override fun onDraw(canvas: Canvas) {
        paint.color = Color.BLACK;
        paint.strokeWidth = 3F;

        canvas.drawRect(300F, 30F, 80F, 80F, paint);

        paint.strokeWidth = 0F;
        paint.color = Color.CYAN;

        canvas.drawRect(20F, 60F, 77F, 77F, paint);

        paint.color = Color.YELLOW;
        canvas.drawRect(33F, 33F, 77F, 60F, paint);

        paint.strokeWidth = 0F;
        paint.color = Color.BLUE;
        val width = this.width
        val height = this.height
        canvas.drawCircle(100F, 40F, 40F, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN ->
                true
            MotionEvent.ACTION_MOVE ->

                true
            MotionEvent.ACTION_UP -> true
            else -> {
                true
            }
        }
    }

    private fun clearCirclePointer() {

    }
}