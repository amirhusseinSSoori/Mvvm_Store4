package com.example.myapplication.data.di

import com.apollographql.apollo.ApolloClient
import com.example.myapplication.data.source.remote.RemoteSource
import com.example.myapplication.data.source.remote.RemoteSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object SourceModule {

    @Provides
    fun provideNetworkSource(network: ApolloClient): RemoteSource {
        return RemoteSourceImp(network)
    }
}