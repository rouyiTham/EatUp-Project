package com.example.eatup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.eatup.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        databaseReference =
            FirebaseDatabase.getInstance("https://learned-skill-377010-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Users")


        val back: TextView = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val next: TextView = findViewById(R.id.next)
        next.setOnClickListener {
            performSignUp()
        }
    }

    fun performSignUp() {
        val email = findViewById<EditText>(R.id.signup_emailText)
        val phone = findViewById<EditText>(R.id.signup_phoneText)
        val password = findViewById<EditText>(R.id.signup_passwordText)
        val confirm_password = findViewById<EditText>(R.id.signup_CpasswordText)

        if (email.text.isEmpty() || password.text.isEmpty() || phone.text.isEmpty()) {
            Toast.makeText(this, "Fields cannot be left empty", Toast.LENGTH_SHORT).show()
            if (confirm_password != password) {
                Toast.makeText(baseContext, "Password not similiar", Toast.LENGTH_SHORT).show()
            }
            return
        }

        val inputEmail = email.text.toString()
        val inputpassword = password.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail, inputpassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MapsActivity::class.java)
                    // before start , store the user's profile into firebase
                    startActivity(intent)

                    Toast.makeText(
                        baseContext, "Success", Toast.LENGTH_SHORT
                    ).show()

                    //save data into realtime dbs
                    val uid = auth.currentUser?.uid
                    val user = User(email.text.toString(), phone.text.toString())

                    if (uid != null) {
                        databaseReference.child(uid).child("Profile").setValue(user).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(baseContext,"Success",Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(baseContext,"Failed",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                } else {
                    Toast.makeText(baseContext, "Authentication failed ", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "Error occurred ${it.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


}









