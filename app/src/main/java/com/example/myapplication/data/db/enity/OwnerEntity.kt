package com.example.myapplication.data.db.enity

import androidx.room.Entity
import androidx.room.PrimaryKey


data class OwnerEntity(
    val id: Int? = null,
    val avatarUrl: String? = null,
    val login: String? = null,
    val url: String? = null
)