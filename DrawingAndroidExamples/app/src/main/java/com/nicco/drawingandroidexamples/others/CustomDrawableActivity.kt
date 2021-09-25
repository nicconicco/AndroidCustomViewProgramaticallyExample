package com.nicco.drawingandroidexamples.others

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CustomDrawableActivity : AppCompatActivity() {
    private lateinit var customDrawableView: CustomDrawableView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customDrawableView = CustomDrawableView(this)

        setContentView(customDrawableView)
    }
}