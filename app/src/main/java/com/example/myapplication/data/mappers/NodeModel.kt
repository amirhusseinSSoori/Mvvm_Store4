package com.example.myapplication.data.mappers

data class NodeModel(
    val id:Int?=null,
    val name: String?=null,
    val ownerEntity: List<OwnerModel>?=null
)