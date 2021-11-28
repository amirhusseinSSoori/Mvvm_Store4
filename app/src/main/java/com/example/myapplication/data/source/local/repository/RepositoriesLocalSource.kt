package com.example.myapplication.data.source.local.repository

import com.example.myapplication.data.db.entity.NodeEntity
import com.example.myapplication.data.db.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface RepositoriesLocalSource {
    fun getListRepository(): Flow<List<NodeEntity>>
    suspend fun updateListRepository(list:List<NodeEntity>)
}