package com.example.myapplication.data.di

import com.example.myapplication.data.mappers.NMapper
import com.example.myapplication.data.repository.Repository
import com.example.myapplication.data.repository.RepositoryImp
import com.example.myapplication.data.source.local.LocalSource
import com.example.myapplication.data.source.remote.RemoteSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(source: RemoteSource,localSource: LocalSource,mapper: NMapper): Repository {
        return RepositoryImp(source,localSource,mapper)
    }
}
