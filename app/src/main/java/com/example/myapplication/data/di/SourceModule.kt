package com.example.myapplication.data.di

import com.apollographql.apollo.ApolloClient
import com.example.myapplication.data.db.dao.GithubDao
import com.example.myapplication.data.source.local.LocalSource
import com.example.myapplication.data.source.local.LocalSourceImp
import com.example.myapplication.data.source.remote.RemoteSource
import com.example.myapplication.data.source.remote.RemoteSourceImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface SourceModule {

    @Binds
    fun provideNetworkSource(networkSourceImp: RemoteSourceImp): RemoteSource

    @Binds
    fun provideLocalSource(localSourceImp: LocalSourceImp): LocalSource
}