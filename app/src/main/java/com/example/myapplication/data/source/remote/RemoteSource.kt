package com.example.myapplication.data.source.remote

import android.provider.ContactsContract
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Response

import example.myapplication.GetListQuery
import example.myapplication.ProfileQuery

interface RemoteSource {
   suspend fun getListRepFromNetwork(): Response<GetListQuery.Data>
}