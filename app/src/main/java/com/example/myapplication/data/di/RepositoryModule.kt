package com.example.myapplication.data.di


import com.example.myapplication.common.Constance.TOKEN
import com.example.myapplication.data.DispatcherProvider
import com.example.myapplication.data.Rep.RepositoryImp

import com.example.myapplication.data.source.local.LocalSource
import com.example.myapplication.data.source.remote.RemoteSource
import com.example.myapplication.domain.repository.Repository
import dagger.Binds

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideRepository(repositoryImp: RepositoryImp): Repository

}







