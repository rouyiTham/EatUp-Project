package com.example.eatup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.eatup.model.ParentModel

class parentItem : AppCompatActivity() {

    var itemNo: TextView = findViewById(R.id.itemNo)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_item)




    }

}