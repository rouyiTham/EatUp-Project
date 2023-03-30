package com.example.eatup.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserFoodInventory")
data class UserFoodInventory(
    @PrimaryKey(autoGenerate = false) val user_id: String,
    @ColumnInfo(name = "foodNo") val foodNo: Int,
    @ColumnInfo(name = "foodName") val foodName : String,
    @ColumnInfo(name = "expiryDate" ) val expiryDate : String,
)
