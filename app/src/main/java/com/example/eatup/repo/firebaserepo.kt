package com.example.eatup.repo

import android.util.Log
import com.example.eatup.model.FoodModel
import com.example.eatup.model.ParentModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class firebaserepo(private var onRealtimeDbTaskComplete: OnRealtimeDbTaskComplete?) {
    private lateinit var auth:FirebaseAuth

    var authid = Firebase.auth.currentUser!!.uid
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance("https://learned-skill-377010-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users")
        .child(authid).child("Inventory")
    val allData: Unit
        get() {
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val parentModelList: MutableList<ParentModel> = ArrayList()

                    for (ds in snapshot.children) {
                        val parentModel = ParentModel()
                        parentModel.buyDate = ds.child("buyDate").getValue(String::class.java)


                        val genericTypeIndicator: GenericTypeIndicator<ArrayList<FoodModel?>?> =
                            object : GenericTypeIndicator<ArrayList<FoodModel?>?>() {}

                        parentModel.setFoodModelList(ds.child("childData").getValue(genericTypeIndicator))
                        parentModelList.add(parentModel)
                    }
                    Log.d("TAG", "onDataChange:$parentModelList")
                    onRealtimeDbTaskComplete?.onSuccess(parentModelList)
                }

                override fun onCancelled(error: DatabaseError) {
                    onRealtimeDbTaskComplete?.onFailure(error)
                }
            })
        }

    interface OnRealtimeDbTaskComplete {
        fun onSuccess(parentModelList: List<ParentModel>)
        fun onFailure(error: DatabaseError)
    }
}