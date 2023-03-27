package com.example.eatup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.eatup.databinding.ActivityBeourpartnerBinding
import com.example.eatup.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class BeourpartnerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBeourpartnerBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeourpartnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homebtn: TextView = findViewById(R.id.homebtn)
        homebtn.setOnClickListener {
            val intent = Intent(this, Homepage1Activity::class.java)
            startActivity(intent)
        }

        val howtousebtn: TextView = findViewById(R.id.howtousebtn)
        howtousebtn.setOnClickListener {
            val intent = Intent(this, HowtouseActivity::class.java)
            startActivity(intent)
        }

        val contactusbtn: TextView = findViewById(R.id.contactusbtn)
        contactusbtn.setOnClickListener {
            val intent = Intent(this, ContactusActivity::class.java)
            startActivity(intent)
        }

        val joinnowwhite: Button = findViewById(R.id.joinnowwhite)
        joinnowwhite.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val submitbtn: Button= findViewById(R.id.submitbtn)
        submitbtn.setOnClickListener {
            performRegister()
        }
    }

    private fun performRegister() {


        val orgname: EditText = findViewById(R.id.organization_name)
        val partneremail: EditText = findViewById(R.id.partner_email)
        val partnerphonenum: EditText= findViewById(R.id.partner_phone_no)
        val partnerwebsite: EditText= findViewById(R.id.partner_website)
        val partnermessage: EditText=findViewById(R.id.partner_message)

        val orgnameinput = orgname.text.toString()
        val partneremailinput = partneremail.text.toString()
        val partnerphonenuminput = partnerphonenum.text.toString()
        val partnerwebsiteinput = partnerwebsite.text.toString()
        val partnermessageinput = partnermessage.text.toString()

        if (orgname.text.isEmpty() || partneremail.text.isEmpty() || partnerphonenum.text.isEmpty() || partnerwebsite.text.isEmpty() || partnermessage.text.isEmpty()) {
            Toast.makeText(this, "Fields cannot be left empty", Toast.LENGTH_SHORT).show()
            return
        }else{
            val intent = Intent(this, TqNgoActivity::class.java)
            startActivity(intent)
        }

        database = FirebaseDatabase.getInstance("https://learned-skill-377010-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("NGOACC")
        val ngoacc = NGOACC(orgnameinput, partneremailinput, partnerphonenuminput, partnerwebsiteinput, partnermessageinput)

        database.child(orgnameinput).setValue(ngoacc).addOnSuccessListener {

           Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {

            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }


    }
}