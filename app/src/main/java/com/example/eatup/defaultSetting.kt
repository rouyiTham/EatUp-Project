package com.example.eatup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.handleCoroutineException

class defaultSetting : AppCompatActivity() {
    var radioGroup: RadioGroup? = null
    var radioGroup2: RadioGroup? = null
    lateinit var S1: RadioButton
    lateinit var S2: RadioButton
    private lateinit var save: Button
    lateinit var userdb: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default_setting)

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        userdb = FirebaseFirestore.getInstance().document("Users/UsersProfile")

        save = findViewById(R.id.saveBtn)

        radioGroup = findViewById(R.id.setting1Btn)
        radioGroup2 = findViewById(R.id.setting2Btn)

        save.setOnClickListener{
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId
            S1 = findViewById(selectedOption)
            val setting1 = S1.text.toString()

            val selectedOption2: Int = radioGroup2!!.checkedRadioButtonId
            S2 = findViewById(selectedOption2)
            val setting2 = S2.text.toString()

            val userSetting = hashMapOf(
            "first-setting" to setting1 ,
            "second-setting" to setting2
            )

            userdb.collection("DefaultSettings").document(userId).set(userSetting).addOnSuccessListener {
            Toast.makeText(this,"Successfully added",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                exception : java.lang.Exception -> Toast.makeText(this,exception.toString(),Toast.LENGTH_LONG).show()
            }
        }

    }}

