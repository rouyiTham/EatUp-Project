package com.example.eatup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eatup.adapter.FoodAdapter
import com.example.eatup.model.FoodModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class nestedItem : AppCompatActivity() {


     lateinit var nestedRV: RecyclerView
     private var checkBox : CheckBox = findViewById(R.id.checkBox)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_item)

        nestedRV.layoutManager = LinearLayoutManager(this)


   }
}

