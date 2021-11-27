package com.example.myapplication.data.source.remote

import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Response
import example.myapplication.GetListQuery

interface RemoteSource {
   suspend fun getListRepFromNetwork(): Response<GetListQuery.Data>
}