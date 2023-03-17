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
        backBtn = findViewById(R.id.backBtn)

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

    }
}