package com.example.eatup.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.annotations.SerializedName
import org.checkerframework.checker.nullness.qual.NonNull

@Entity("ProductItems")
data class WebDataItem(
    val expiration_date: String?,
    val identifier: String?,
    @PrimaryKey val product_id: Int?,
    val product_name: String?,
    val quantity: Int?
)

