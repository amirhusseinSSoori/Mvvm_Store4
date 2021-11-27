package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.domain.exception.ApolloResult
import kotlinx.coroutines.flow.Flow

interface Repository {
     fun getListRepFromSource(): Flow<ApolloResult<List<NodeModel>>>
}