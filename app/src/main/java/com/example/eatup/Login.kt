package com.example.eatup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.eatup.databinding.ActivityLogin2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        auth = Firebase.auth

        val registerBtn: TextView = findViewById(R.id.registerBtn)
        registerBtn.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        val loginBtn: Button = findViewById(R.id.loginBtn)
        loginBtn.setOnClickListener{
            performLogin()

        }

        val forgotpasswordBtn: TextView = findViewById(R.id.fpBtn)
        forgotpasswordBtn.setOnClickListener {
            val intent = Intent(this,ForgotPassword::class.java)
            startActivity(intent)
        }

        val partnerBtn: TextView = findViewById(R.id.partnerBtn)
        partnerBtn.setOnClickListener{
            val intent = Intent(this, BeourpartnerActivity:: class.java)
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

    private fun performLogin() {
        val email:EditText = findViewById(R.id.login_emailText)
        val password:EditText = findViewById(R.id.login_passwordText)

        if(email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Please fill all the fields ", Toast.LENGTH_SHORT).show()
            return
        }

        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if(emailInput == "ngo@gmail.com" && passwordInput == "123456"){
                        val intent1 = Intent(this,NgoAlertActivity::class.java)
                        startActivity(intent1)
                    }
                    else {
                        val intent2 = Intent(this, scanPage::class.java)
                        startActivity(intent2)
                    }

                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Authentication failed. ${it.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
            }
    }
}



