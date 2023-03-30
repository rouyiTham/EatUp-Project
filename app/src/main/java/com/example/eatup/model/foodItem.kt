package com.example.eatup.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.type.Date
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "FoodItem")
data class FoodItem(
    @PrimaryKey(autoGenerate = false) val foodNo: Int,
    @ColumnInfo(name = "foodName") val foodName : String,
    @ColumnInfo(name = "expiryDate" ) val expiryDate : String,
)

