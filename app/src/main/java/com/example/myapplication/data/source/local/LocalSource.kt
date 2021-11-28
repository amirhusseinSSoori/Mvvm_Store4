package com.example.myapplication.data.source.local

import com.example.myapplication.data.db.entity.NodeEntity
import com.example.myapplication.data.db.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    fun getListRepository(): Flow<List<NodeEntity>>
    suspend fun updateListRepository(list:List<NodeEntity>)

    fun getProfileRepository(): Flow<ProfileEntity>
    suspend fun updateProfileRepository(data:ProfileEntity)
}