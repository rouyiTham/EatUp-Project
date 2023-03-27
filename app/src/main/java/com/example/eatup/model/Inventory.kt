package com.example.eatup.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class Inventory(
    @PrimaryKey(autoGenerate = false) val receipt_Tag : Int,
    @ColumnInfo(name = "buyDate") val buyDate : String,
    //@ColumnInfo(name = "id") val id: String = Firebase.auth.currentUser!!.uid,
)


