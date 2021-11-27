package com.example.myapplication.data.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Input
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
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(NetworkInterceptor())
            .build()
    }

    fun getCharacters(): ApolloQueryCall<ProfileQuery.Data> =
        apolloClient().query(ProfileQuery())

    private fun apolloClient(): ApolloClient =
        ApolloClient.builder().okHttpClient(
            OkHttpClient.Builder()
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(NetworkInterceptor())
                .build()
        ).serverUrl("https://api.github.com/graphql").build()

    @Singleton
    @Provides
    fun apolloClient(httpClient: OkHttpClient): ApolloClient =
        ApolloClient.builder()
            .okHttpClient(httpClient).serverUrl("https://api.github.com/graphql").build()

}