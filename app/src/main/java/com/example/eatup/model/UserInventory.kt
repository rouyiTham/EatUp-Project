package com.example.eatup.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "UserInventory")
data class UserInventory(
    @PrimaryKey(autoGenerate = false) val user_id: String,
    @ColumnInfo(name = "Inventory_ID") val inventory_id : Int

)
