package com.amirhusseinsoori.apollotask.data.di


import com.amirhusseinsoori.apollotask.data.repository.AccountRepositoryImp
import com.amirhusseinsoori.apollotask.data.repository.RepositoryImp
import com.amirhusseinsoori.apollotask.domain.repository.ProfileRepositry

import com.amirhusseinsoori.apollotask.domain.repository.Repository
import dagger.Binds

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideRepository(repositoryImp: RepositoryImp): Repository
    @Binds
    fun provideProfileRepository(accountRepositoryImp: AccountRepositoryImp): ProfileRepositry
}







