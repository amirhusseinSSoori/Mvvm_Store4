package com.amirhusseinsoori.apollotask.data.di

import com.apollographql.apollo.ApolloClient
import com.amirhusseinsoori.apollotask.common.Constance.BaseUrl
import com.amirhusseinsoori.apollotask.data.DispatcherProvider
import com.amirhusseinsoori.apollotask.data.network.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RequestModule {

    @Provides
    fun provideHttpClient(networkInterceptor: NetworkInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(networkInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun apolloClient(httpClient: OkHttpClient): ApolloClient =
        ApolloClient.builder()
            .okHttpClient(httpClient).serverUrl(BaseUrl).build()

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