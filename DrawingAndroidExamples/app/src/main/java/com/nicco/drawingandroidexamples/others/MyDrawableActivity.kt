package com.nicco.drawingandroidexamples.others

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.nicco.drawingandroidexamples.R

class MyDrawableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_drawable)

        val myDrawing = MyDrawable()
        val image: ImageView = findViewById(R.id.img)
        image.setImageDrawable(myDrawing)
    }
}