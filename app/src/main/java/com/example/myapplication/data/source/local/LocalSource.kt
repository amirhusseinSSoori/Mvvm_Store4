package com.example.myapplication.data.source.local

import com.example.myapplication.data.db.enity.NodeEntity

interface LocalSource {

    suspend fun getListRepository():List<NodeEntity>
    suspend fun insertListRepository(list:List<NodeEntity>)

}