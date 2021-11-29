package com.example.myapplication.data.di


import com.example.myapplication.data.Rep.AccountRepositoryImp
import com.example.myapplication.data.Rep.RepositoryImp
import com.example.myapplication.domain.repository.ProfileRepositry

import com.example.myapplication.domain.repository.Repository
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







