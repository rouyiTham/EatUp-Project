package com.example.eatup

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPassword : AppCompatActivity() {
    private lateinit var resetPText : EditText
    private lateinit var resetBtn : Button
    private lateinit var backBtn : TextView
    private lateinit var auth : FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = Firebase.auth

        resetPText = findViewById(R.id.resetPText)
        resetBtn = findViewById(R.id.resetBtn)
        backBtn = findViewById(R.id.backbtn)

        resetBtn.setOnClickListener{
            val sPassword = resetPText.text.toString()
            auth.sendPasswordResetEmail(sPassword)
                .addOnSuccessListener {
                    Toast.makeText(this,"Email has been sent",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                }
        }

        backBtn.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        val homebtn:TextView = findViewById(R.id.homebtn)
        homebtn.setOnClickListener {
            val intent = Intent(this,Homepage1Activity::class.java)
            startActivity(intent)
        }

        val howtousebtn:TextView=findViewById(R.id.howtousebtn)
        howtousebtn.setOnClickListener {
            val intent= Intent(this,HowtouseActivity::class.java)
            startActivity(intent)
        }

        val contactusbtn:TextView = findViewById(R.id.contactusbtn)
        contactusbtn.setOnClickListener {
            val intent = Intent(this,ContactusActivity::class.java)
            startActivity(intent)
        }

        val joinnowwhite:Button= findViewById(R.id.joinnowwhite)
        joinnowwhite.setOnClickListener {
            val intent= Intent(this,Login::class.java)
            startActivity(intent)
        }

    }
}