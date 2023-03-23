package com.example.eatup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.eatup.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class defaultSetting : AppCompatActivity() {
    var radioGroup: RadioGroup? = null
    var radioGroup2: RadioGroup? = null
    lateinit var S1: RadioButton
    lateinit var S2: RadioButton
    private lateinit var save: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default_setting)

        auth = Firebase.auth
        databaseReference =
            FirebaseDatabase.getInstance("https://learned-skill-377010-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Users")
        //val uid = FirebaseAuth.getInstance().currentUser!!.uid
        // userdb = FirebaseFirestore.getInstance().document("Users/UsersProfile")

        save = findViewById(R.id.saveBtn)

        radioGroup = findViewById(R.id.setting1Btn)
        radioGroup2 = findViewById(R.id.setting2Btn)

        save.setOnClickListener {
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId
            S1 = findViewById(selectedOption)
            val setting1 = S1.text.toString()

            val selectedOption2: Int = radioGroup2!!.checkedRadioButtonId
            S2 = findViewById(selectedOption2)
            val setting2 = S2.text.toString()

            val userSetting = hashMapOf(
                "first-setting" to setting1,
                "second-setting" to setting2
            )

            val uid = auth.currentUser?.uid

            if (uid != null) {
                databaseReference.child(uid).child("Setting").setValue(userSetting).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(baseContext,"Success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(baseContext,"Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            val intent = Intent(this, scanPage::class.java)
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

        val joinnowwhite:Button= findViewById(R.id.joinnowwhite)
        joinnowwhite.setOnClickListener {
            val intent= Intent(this,Login::class.java)
            startActivity(intent)
        }
    }}

