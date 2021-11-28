package com.example.myapplication.data.db.entity

import androidx.room.Entity


@Entity(tableName = "profileEntity", primaryKeys = ["id"])
data class ProfileEntity(
    var id: Int? = null,
    var login: String? = null,
    var avatarUrl: String? = null,
    var url: String? = null,
)