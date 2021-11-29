package com.amirhusseinsoori.apollotask.data.source.local.repository

import com.amirhusseinsoori.apollotask.data.db.entity.NodeEntity
import kotlinx.coroutines.flow.Flow

interface RepositoriesLocalSource {
    fun getListRepository(): Flow<List<NodeEntity>>
    suspend fun updateListRepository(list:List<NodeEntity>)
}