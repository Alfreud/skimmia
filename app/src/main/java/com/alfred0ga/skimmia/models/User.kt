package com.alfred0ga.skimmia.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val userName: String,
    val name: String,
    val lastName: String,
    val description: String,
    val photo: Bitmap? = null
)
