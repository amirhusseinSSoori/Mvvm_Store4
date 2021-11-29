package com.amirhusseinsoori.apollotask.data.di

import com.amirhusseinsoori.apollotask.data.source.local.account.AccountLocalSource
import com.amirhusseinsoori.apollotask.data.source.local.account.AccountImpLocalSource
import com.amirhusseinsoori.apollotask.data.source.local.repository.RepositoriesLocalSource
import com.amirhusseinsoori.apollotask.data.source.local.repository.RepositoriesLocalSourceImp
import com.amirhusseinsoori.apollotask.data.source.remote.RemoteSource
import com.amirhusseinsoori.apollotask.data.source.remote.RemoteSourceImp
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