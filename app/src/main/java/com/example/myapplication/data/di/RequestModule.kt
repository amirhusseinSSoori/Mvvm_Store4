package com.example.myapplication.data.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Input
import com.example.myapplication.common.Constance
import com.example.myapplication.common.Constance.BaseUrl
import com.example.myapplication.data.network.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import example.myapplication.ProfileQuery
import okhttp3.Interceptor
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

}