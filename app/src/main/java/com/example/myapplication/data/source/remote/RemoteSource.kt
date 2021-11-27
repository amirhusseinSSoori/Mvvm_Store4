package com.example.myapplication.data.source.remote

import com.example.myapplication.domain.exception.ApolloResult
import example.myapplication.GetListQuery

interface RemoteSource {
   suspend fun getListRepFromNetwork(): ApolloResult<GetListQuery.Data?>
}