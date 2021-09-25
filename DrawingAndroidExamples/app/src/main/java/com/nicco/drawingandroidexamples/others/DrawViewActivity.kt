package com.nicco.drawingandroidexamples.others

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DrawViewActivity : AppCompatActivity() {
    var drawView: DrawView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drawView = DrawView(this)
        drawView!!.setBackgroundColor(Color.WHITE)

        setContentView(drawView)
    }
}