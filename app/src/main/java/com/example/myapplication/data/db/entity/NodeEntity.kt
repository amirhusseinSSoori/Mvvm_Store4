package com.example.myapplication.data.db.entity

import androidx.room.Entity

@Entity(tableName = "nodeEntity", primaryKeys = ["id"])
data class NodeEntity(
    val id: Int? = null,
    val name: String? = null,
    val ownerEntity: OwnerEntity ?= null
)