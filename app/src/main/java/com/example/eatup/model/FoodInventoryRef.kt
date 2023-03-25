package com.example.eatup.model

import androidx.room.Entity

@Entity(
    primaryKeys = ["receipt_Tag","foodNo"]
)
data class FoodInventoryRef(
    val receipt_Tag : Int,
    val foodNo : Int
)
