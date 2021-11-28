package com.example.myapplication.data.di

import com.example.myapplication.data.source.local.account.AccountLocalSource
import com.example.myapplication.data.source.local.account.AccountImpLocalSource
import com.example.myapplication.data.source.local.repository.RepositoriesLocalSource
import com.example.myapplication.data.source.local.repository.RepositoriesLocalSourceImp
import com.example.myapplication.data.source.remote.RemoteSource
import com.example.myapplication.data.source.remote.RemoteSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface SourceModule {

    @Binds
    fun provideNetworkSource(networkSourceImp: RemoteSourceImp): RemoteSource

    @Binds
    fun provideRepLocalSource(RepLocalSourceImp: RepositoriesLocalSourceImp): RepositoriesLocalSource

    @Binds
    fun provideAccountLocalSource(accountLocalSourceImp: AccountImpLocalSource): AccountLocalSource
}