package com.example.myapplication.domain.repository

import com.example.myapplication.domain.exption.SSOTResult
import com.example.myapplication.domain.model.NodeModel
import kotlinx.coroutines.flow.Flow


interface Repository {
    suspend fun getLatestRepositories(): Flow<SSOTResult<List<NodeModel>>>
}