package com.example.eatup.model

import android.widget.EditText
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.checkerframework.checker.nullness.qual.NonNull

@Entity("User")
data class User (
    @PrimaryKey
    val email : String,
    val phone : String?
)
