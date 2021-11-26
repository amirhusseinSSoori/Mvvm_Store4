package com.example.myapplication.data.source.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import com.example.myapplication.R
import com.example.myapplication.data.network.errorHandle.ApolloResult
import com.example.myapplication.data.network.errorHandle.DataSourceException
import example.myapplication.GetListQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteSourceImp @Inject constructor(val api: ApolloClient) : RemoteSource {
    override suspend fun getListRepFromNetwork(owner: String): ApolloResult<GetListQuery.Data?>  {
        return try {
            val result = api.query(GetListQuery(owner)).await()
            if (result.hasErrors()) {
                ApolloResult.Error(DataSourceException.Server(result.errors?.first()))
            } else {
                ApolloResult.Success(result.data)
            }
        } catch (e: Exception) {
            ApolloResult.Error(DataSourceException.Unexpected(R.string.app_name))
        }
    }
}