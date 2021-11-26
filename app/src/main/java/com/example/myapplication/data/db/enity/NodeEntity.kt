package com.example.myapplication.data.db.enity

import androidx.room.Embedded
import androidx.room.Entity
import example.myapplication.GetListQuery

@Entity(tableName = "nodeEntity", primaryKeys = ["id"])
data class NodeEntity(
    val id: Int? = null,
    val name: String? = null,
    val ownerEntity: OwnerEntity ?= null
)