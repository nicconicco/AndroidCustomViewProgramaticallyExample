package com.nicco.drawingandroidexamples.others

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.MotionEvent

import android.R
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat


class TutorialView(context: Context, attributeSet: AttributeSet): View(context) {

    private var mIcon: Drawable? = null
    private var mPosX = 0f
    private var mPosY = 0f

    private var mLastTouchX = 0f
    private var mLastTouchY = 0f

    init {
        mIcon = ContextCompat.getDrawable(context, R.drawable.ic_input_add)
        mIcon!!.setBounds(0, 0, mIcon!!.intrinsicWidth, mIcon!!.intrinsicHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.translate(mPosX, mPosY)

        mIcon!!.draw(canvas)
        canvas.restore()
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        val action = ev!!.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                val x = ev.x
                val y = ev.y

                // Remember where we started
                mLastTouchX = x
                mLastTouchY = y
            }
            MotionEvent.ACTION_MOVE -> {
                val x = ev.x
                val y = ev.y

                // Calculate the distance moved
                val dx = x - mLastTouchX
                val dy = y - mLastTouchY

                // Move the object
                mPosX += dx
//                mPosY += dy

                // Remember this touch position for the next move event
                mLastTouchX = x
                mLastTouchY = y

                // Invalidate to request a redraw
                invalidate()
            }
        }

        return true
    }
}