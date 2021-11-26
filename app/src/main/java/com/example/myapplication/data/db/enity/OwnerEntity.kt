package com.example.myapplication.data.db.enity

import androidx.room.Entity


data class OwnerEntity(
    val avatarUrl: String?=null,
    val login: String?=null,
    val url: String?=null
)