package com.example.eatup

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eatup.adapter.NGOAdapter
import com.example.eatup.database.database
import com.example.eatup.databinding.ActivityEachNgoAlertBinding
import com.example.eatup.model.RetrievalData
import com.example.eatup.viewmodel.NGOviewmodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class NgoAlertActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var ngo_rv : RecyclerView
    private lateinit var ngoArrayList : ArrayList<RetrievalData>
    private lateinit var builder: AlertDialog.Builder

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ngo_alert)

        ngo_rv = findViewById(R.id.ngo_rv)
        ngo_rv.layoutManager = LinearLayoutManager(this)
        ngo_rv.setHasFixedSize(true)


        ngoArrayList = arrayListOf<RetrievalData>()
        getNGOData()

    }

    private fun getNGOData() {

        dbref = FirebaseDatabase.getInstance("https://learned-skill-377010-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("NGO")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (dataSnapshot in snapshot.children){

                        val ngolist = dataSnapshot.getValue(RetrievalData::class.java)
                        ngoArrayList.add(ngolist!!)

                    }

                    ngo_rv.adapter = NGOAdapter(ngoArrayList)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}
