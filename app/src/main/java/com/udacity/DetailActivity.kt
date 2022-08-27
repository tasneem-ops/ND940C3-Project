package com.udacity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val nameText = findViewById<TextView>(R.id.textView2)
        startAnimation(nameText)
        setSupportActionBar(toolbar)
        val name = intent.getStringExtra("file_name")
        val status = intent.getStringExtra("status")

        val statusText = findViewById<TextView>(R.id.textView4)
        nameText.text = name
        statusText.text = status
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            onOKClicked()
        }

    }

    fun startAnimation(view : View){
        val animator = ObjectAnimator.ofFloat(view, View.ROTATION, -360f, 0f)
        animator.start()
    }
    fun onOKClicked(){
        val mainIntent = Intent(applicationContext, MainActivity::class.java)
        startActivity(mainIntent)
    }

}
