package com.example.myapplication.data.source.local

import com.example.myapplication.data.db.enity.NodeEntity
import kotlinx.coroutines.flow.Flow

interface LocalSource {

    suspend fun getListRepository(): Flow<List<NodeEntity>>
    suspend fun insertListRepository(list:List<NodeEntity>)

}