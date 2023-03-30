package com.example.eatup.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

@Entity(tableName = "NGOdata")
data class NGOdata(
    @PrimaryKey(autoGenerate = false) val userId: String = Firebase.auth.currentUser!!.uid,
    @ColumnInfo(name = "Email") val email: Any?,
    @ColumnInfo(name = "Phone_no") val phone_no: Any?,
    @ColumnInfo(name = "Address") val address: Any?,
    @ColumnInfo(name = "Time") val time: String?,
    //@ColumnInfo(name = "C_foodItem") val c_foodItems: MutableList<ContributeFoodItems>?
)

class CfoodTypeConverter {
    @TypeConverter
    /*fun fromString(value: String?): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromInventoryArrayList(list: List<String?>): String {
        return Gson().toJson(list)
    }*/

  /*  fun frmListNull (list: List<ContributeFoodItems?>?): String? {
        return Gson()?.toJson(list)
    }*/

    fun fromNul (value: Any? ): String? {
        return value?.toString()
    }


}