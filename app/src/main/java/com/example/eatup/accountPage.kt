package com.example.eatup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.TextView
import android.widget.Toast
import com.example.eatup.databinding.ActivityAccountPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class accountPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityAccountPageBinding
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val authid = Firebase.auth.currentUser!!.uid

        databaseReference =
            FirebaseDatabase.getInstance("https://learned-skill-377010-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Users")

        databaseReference.child(authid).get().addOnSuccessListener {
            if (it.exists()) {
                Toast.makeText(this, "Successfully read", Toast.LENGTH_SHORT).show()
                val emailtext = it.child("Profile").child("email").value
                val phonetext = it.child("Profile").child("phone").value
                val settingtext1 = it.child("Setting").child("first-setting").value
                val settingtext2 = it.child("Setting").child("second-setting").value

                binding.emailText.text = emailtext.toString()
                binding.phoneText.text = phonetext.toString()
                binding.setting1Text.text = settingtext1.toString()
                binding.setting2Text.text = settingtext2.toString()

            } else {
                Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load user info", Toast.LENGTH_SHORT).show()
        }
    }
}
