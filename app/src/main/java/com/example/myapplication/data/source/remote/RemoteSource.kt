package com.example.myapplication.data.source.remote

import com.apollographql.apollo.ApolloQueryCall
import com.example.myapplication.data.network.errorHandle.ApolloResult
import example.myapplication.GetListQuery

interface RemoteSource {
   suspend fun getListRepFromNetwork(owner: String): ApolloResult<GetListQuery.Data?>
}