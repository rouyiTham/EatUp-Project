package com.example.eatup

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Homepage2Activity : AppCompatActivity() {
    private lateinit var joinowBtn2 : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage2)

        joinowBtn2 = findViewById(R.id.joinowBtn2)
        joinowBtn2.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val homebtn: TextView = findViewById(R.id.homebtn)
        homebtn.setOnClickListener {
            val intent = Intent(this,Homepage1Activity::class.java)
            startActivity(intent)
        }

        val howtousebtn: TextView =findViewById(R.id.howtousebtn)
        howtousebtn.setOnClickListener {
            val intent= Intent(this,HowtouseActivity::class.java)
            startActivity(intent)
        }

        val contactusbtn: TextView = findViewById(R.id.contactusbtn)
        contactusbtn.setOnClickListener {
            val intent = Intent(this,ContactusActivity::class.java)
            startActivity(intent)
        }

        val joinnowwhite: Button = findViewById(R.id.joinnowwhite)
        joinnowwhite.setOnClickListener {
            val intent= Intent(this,Login::class.java)
            startActivity(intent)
        }
    }
}