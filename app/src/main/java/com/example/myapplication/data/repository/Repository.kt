package com.example.myapplication.data.repository

import com.apollographql.apollo.ApolloQueryCall
import com.example.myapplication.data.db.enity.NodeEntity


import com.example.myapplication.data.mappers.NodeModel
import com.example.myapplication.data.network.errorHandle.ApolloResult
import example.myapplication.GetListQuery
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getListRepFromSource(): Flow<ApolloResult<List<NodeModel>>>
}