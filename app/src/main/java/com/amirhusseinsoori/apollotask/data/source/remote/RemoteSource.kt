package com.amirhusseinsoori.apollotask.data.source.remote

import com.apollographql.apollo.api.Response
import example.myapplication.GetListQuery
import example.myapplication.ProfileQuery


interface RemoteSource {
   suspend fun getListRepFromNetwork(): Response<GetListQuery.Data>
   suspend fun getProfileFromNetwork(): Response<ProfileQuery.Data>
}