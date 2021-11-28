package com.example.myapplication.data.di


import com.example.myapplication.common.Constance.TOKEN
import com.example.myapplication.data.DispatcherProvider
import com.example.myapplication.data.Rep.RepositoryImp

import com.example.myapplication.data.source.local.LocalSource
import com.example.myapplication.data.source.remote.RemoteSource
import com.example.myapplication.domain.repository.Repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    fun provideRepository(source: RemoteSource,localSource: LocalSource,dispatcher: DispatcherProvider): Repository {
        return RepositoryImp(source,localSource,dispatcher)
    }


    @Provides
    fun providerDispatcher():DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }
}







