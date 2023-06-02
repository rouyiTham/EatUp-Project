package com.example.eatup.model

import androidx.room.*
import com.google.common.reflect.TypeToken
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

@Entity
data class ContributeFoodItems(
    @PrimaryKey(autoGenerate = false) val userId : String = Firebase.auth.currentUser!!.uid,
   // @ColumnInfo(name = "foodName") val foodName : String,
    @ColumnInfo(name = "foodItems") val foodItems : ArrayList<String>,

)
class InventoryTypeConverter {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromInventoryArrayList(list: ArrayList<String?>): String {
        return Gson().toJson(list)
    }
}