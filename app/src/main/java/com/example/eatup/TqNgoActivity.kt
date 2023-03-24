package com.example.eatup

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TqNgoActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tq_ngo)

        val backbtnhome: Button = findViewById(R.id.backbtnhome)
        backbtnhome.setOnClickListener {
            val intent= Intent(this,Homepage2Activity::class.java)
            startActivity(intent)
        }
    }
}