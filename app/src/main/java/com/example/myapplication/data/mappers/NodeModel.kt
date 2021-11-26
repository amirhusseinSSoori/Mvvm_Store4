package com.example.myapplication.data.mappers

import com.example.myapplication.data.db.enity.NodeEntity
import com.example.myapplication.data.db.enity.OwnerEntity
import example.myapplication.GetListQuery

data class NodeModel(
    val name: String?=null,
    val owner: OwnerModel?=null
)

data class OwnerModel(
    val avatarUrl: String?=null,
    val login: String?=null,
    val url: String?=null
)





