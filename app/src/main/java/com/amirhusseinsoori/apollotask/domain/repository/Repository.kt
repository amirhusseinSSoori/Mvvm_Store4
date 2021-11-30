package com.amirhusseinsoori.apollotask.domain.repository

import com.dropbox.android.external.store4.Store
import com.amirhusseinsoori.apollotask.data.db.entity.NodeEntity
import com.amirhusseinsoori.apollotask.domain.exption.Result
import com.amirhusseinsoori.apollotask.domain.model.NodeModel
import kotlinx.coroutines.flow.Flow


interface Repository {
    suspend fun getLatestRepositories(): Flow<Result<List<NodeModel>>>
    fun getStore(): Store<String, List<NodeEntity>>
}