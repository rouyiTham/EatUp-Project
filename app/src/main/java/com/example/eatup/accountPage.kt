package com.example.eatup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
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

        val drawerLayout: DrawerLayout = binding.drawerLayout

        imageMenu = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(imageMenu)
        imageMenu.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView = binding.navigationView

        //to navigate to each page
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                }
                R.id.scan -> {
                    val intent = Intent(this, scanPage::class.java)
                    startActivity(intent)
                }
                R.id.resources -> {
                    val intent = Intent(this,Resources::class.java)
                    startActivity(intent)
                }
                R.id.inventory -> {
                    val intent = Intent(this,FoodInventory::class.java)
                    startActivity(intent)
                }
                R.id.account -> {
                    val intent = Intent(this,accountPage::class.java)
                    //startActivity(intent)
                }
            }
            true
        }

        //allow to select the menu items in menu.xml//
        fun onOptionsItemSelected(item: MenuItem): Boolean {
            if(imageMenu.onOptionsItemSelected(item)) {
                return true
            }
            return super.onOptionsItemSelected(item)
        }

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
