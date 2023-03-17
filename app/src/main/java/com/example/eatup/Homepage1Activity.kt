package com.example.eatup

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Homepage1Activity : AppCompatActivity() {
    private lateinit var joinowBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage1)

        joinowBtn = findViewById(R.id.joinowBtn)
        joinowBtn.setOnClickListener{
            val intent = Intent(this, Homepage2Activity::class.java)
            startActivity(intent)
        }
    }
}

