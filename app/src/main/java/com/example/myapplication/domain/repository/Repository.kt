package com.example.myapplication.domain.repository

import com.dropbox.android.external.store4.Store
import com.example.myapplication.data.db.enity.NodeEntity
import com.example.myapplication.domain.exption.MyResult
import com.example.myapplication.domain.model.NodeModel
import kotlinx.coroutines.flow.Flow


interface Repository {
    suspend fun getLatestRepositories(): Flow<MyResult<List<NodeModel>>>
}