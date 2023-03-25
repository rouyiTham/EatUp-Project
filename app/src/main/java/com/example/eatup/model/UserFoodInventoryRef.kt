package com.example.eatup.model

import androidx.room.Entity

@Entity(
    primaryKeys = ["user_id","foodNo"]
)
data class UserFoodInventoryRef(
    val user_id : String,
    val foodNo : Int
)
