package com.example.eatup.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

@Entity ("ProductData")
data class UserFood(
    @PrimaryKey(autoGenerate = false) val userId : String = Firebase.auth.currentUser!!.uid,
    @ColumnInfo(name = "foodItems") val WebfoodItems : MutableList<WebDataItem>,
    )

class UserFoodConverter {
    @TypeConverter
    fun fromString(value: String?): MutableList<WebDataItem> {
        val listType = object : TypeToken<ArrayList<WebDataItem>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromWebInventoryArrayList(list: MutableList<WebDataItem?>): String? {
        return Gson().toJson(list)
    }
}

