package com.example.myapplication.data.repository

import android.util.Log
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.coroutines.await
import com.example.myapplication.data.network.errorHandle.ApolloResult
import com.example.myapplication.data.source.remote.RemoteSource
import example.myapplication.GetListQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(val network:RemoteSource):Repository {
    override suspend fun getListRepFromSource(owner: String): ApolloResult<GetListQuery.Data?> {
        return network.getListRepFromNetwork(owner)
    } }