package com.example.myapplication.data.db.enity

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "node", primaryKeys = ["id"])
data class NodeEntity(
    val id: Int? = null,
    val name: String? = null,
    @Embedded
    val ownerEntity: OwnerEntity? = null
)