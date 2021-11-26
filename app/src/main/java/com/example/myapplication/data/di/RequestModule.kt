package com.example.myapplication.data.di

import com.apollographql.apollo.ApolloClient
import com.example.myapplication.data.network.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RequestModule {

    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(NetworkInterceptor())
            .build()
    }


    @Singleton
    @Provides
     fun apolloClient(httpClient: OkHttpClient): ApolloClient =
        ApolloClient.builder()
            .okHttpClient(httpClient).serverUrl("https://api.github.com/graphql").build()

}