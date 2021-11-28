package com.example.myapplication.data.source.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await


import example.myapplication.GetListQuery
import example.myapplication.ProfileQuery

import javax.inject.Inject

class RemoteSourceImp @Inject constructor(val network: ApolloClient) : RemoteSource {
    override suspend fun getListRepFromNetwork(): Response<GetListQuery.Data> {
        return network.query(GetListQuery()).await()
    }

    override suspend fun getProfileFromNetwork(): Response<ProfileQuery.Data> {
        return network.query(ProfileQuery()).await()
    }


}