package com.example.eatup.viewmodel

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

open class ViewModel {
    val databaseInsertion : DatabaseReference = FirebaseDatabase.getInstance("https://learned-skill-377010-default-rtdb.asia-southeast1.firebasedatabase.app")
        .getReference("NGO").child(Firebase.auth.currentUser!!.uid)
    fun insertEmail(email : Any?){
        databaseInsertion.child("Email").setValue(email)
    }

    fun insertPhone(phone: Any?){
        databaseInsertion.child("Phone").setValue(phone)
    }

    fun inserttime(time: String?) {
        databaseInsertion.child("PickUpTime").setValue(time)
    }

    fun insertAddress(address : Any?){

        databaseInsertion.setValue(address)
    }

    fun insertAddress2(address: Any?){
        databaseInsertion.setValue(address)
    }

    //using while loop to tranverse through the foodItemList and add into the list//
   /* fun insertFoodItems(c_foodItems: MutableList<ContributeFoodItems>){
        val Cc_foodItems = mutableListOf<String>()
        //val Ccc_foodItems = ArrayList<ContributeFoodItems>()

        var i = 0
        while(i < c_foodItems.size){
            val datalist =  DataList(foodName = c_foodItems[i].foodItems.toString())
            datalist.foodName?.get(i)?.let { Cc_foodItems.add(it.toString()) }
            i++
        }
        databaseInsertion.setValue(Cc_foodItems)
    }*/

    fun insertfood(foodItems: String){
        databaseInsertion.child("FoodToBeContributed").setValue(foodItems)
    }


   /* constructor(){}
    constructor(foodItems : ArrayList<String>?) {
        this.foodItems = foodItems
    }
    fun addItems(foodItems: ArrayList<String>?){
        this.foodItems = foodItems
    }

    fun getItems(): ArrayList<String>?{
        return foodItems
    }*/

}
