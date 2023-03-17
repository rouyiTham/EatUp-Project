package com.example.eatup.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

@Entity(tableName = "ContributeUser")
data class Contribute (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : String?,
    @ColumnInfo(name = "email") val email : String?,
    @ColumnInfo(name = "phone") val phone : String?,
    @ColumnInfo(name = "address") val address : String?,
    @ColumnInfo(name = "date") val date : String?,
    @ColumnInfo(name = "time") val time : String?,
    @ColumnInfo(name = "Inventory") val inventory : ArrayList<String>
)
class ItemTypeConverter {
    @TypeConverter
    fun fromString(value : String?): ArrayList<String>{
        val listType = object : TypeToken<ArrayList<String>>(){}.type
        return Gson().fromJson(value , listType)
    }
}

@TypeConverter
fun fromArrayList(list:ArrayList<String?>):String {
    return Gson().toJson(list)
}

