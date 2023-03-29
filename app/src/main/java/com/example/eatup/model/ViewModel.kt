package com.example.eatup.model

import com.example.eatup.database.database
class ViewModel {
    var foodItems : ArrayList<String>?=null

    constructor(){}
    constructor(foodItems : ArrayList<String>?) {
        this.foodItems = foodItems
    }
    fun addItems(foodItems: ArrayList<String>?){
        this.foodItems = foodItems
    }

    fun getItems(): ArrayList<String>?{
        return foodItems
    }

}
