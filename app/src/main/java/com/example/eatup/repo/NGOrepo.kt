package com.example.eatup.repo

import androidx.lifecycle.MutableLiveData
import com.example.eatup.model.RetrievalData
import com.google.firebase.database.*

class NGOrepo{

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("NGO")

    @Volatile private var INSTANCE : NGOrepo ?= null

    fun getInstance() : NGOrepo{
        return INSTANCE ?: synchronized(this){

            val instance = NGOrepo()
            INSTANCE = instance
            instance
        }


    }


    fun loadUsers(ngoList : MutableLiveData<List<RetrievalData>>){

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    val _ngoList : List<RetrievalData> = snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(RetrievalData::class.java)!!

                    }

                    ngoList.postValue(_ngoList)

                }catch (e : Exception){


                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }

}