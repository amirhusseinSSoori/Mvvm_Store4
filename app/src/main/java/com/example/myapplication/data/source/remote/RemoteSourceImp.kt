package com.example.myapplication.data.source.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.example.myapplication.R
import com.example.myapplication.domain.exception.ApolloResult
import com.example.myapplication.domain.exception.DataSourceException
import example.myapplication.GetListQuery
import javax.inject.Inject

class RemoteSourceImp @Inject constructor(val network: ApolloClient) : RemoteSource {
    override suspend fun getListRepFromNetwork(): ApolloResult<GetListQuery.Data?>  {
        return try {
            val result = network.query(GetListQuery()).await()
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