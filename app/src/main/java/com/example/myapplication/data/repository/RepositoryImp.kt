package com.example.myapplication.data.repository

import android.util.Log
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.coroutines.await
import com.example.myapplication.data.mappers.NodeMapper
import com.example.myapplication.data.network.errorHandle.ApolloResult
import com.example.myapplication.data.source.local.LocalSource
import com.example.myapplication.data.source.remote.RemoteSource
import example.myapplication.GetListQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    val network: RemoteSource,
    val local: LocalSource,
    val mapper: NodeMapper
) : Repository {
    override suspend fun getListRepFromSource(owner: String): Flow<ApolloResult<GetListQuery.Data?>> =
        flow {
            when (val result = network.getListRepFromNetwork(owner)) {
                is ApolloResult.Success -> {
                    result.data?.let {
                        it.repositoryOwner!!.repositories.nodes?.let { data ->
                            emit(ApolloResult.Success(this))
                            local.insertListRepository(mapper.mapFromEntityList(data as List<GetListQuery.Node>))
                        }
                    }
                }
                is ApolloResult.Error -> {
                    val listCharacters = local.getListRepository()
                    if (listCharacters.isNotEmpty()) {
                        emit(
                            ApolloResult.Success(
                                mapper.mapToEntityList(listCharacters)
                            )
                        )
                    } else {
                        emit(ApolloResult.Error(result.exception))
                    }
                }
            }
        }.onStart { emit(ApolloResult.Loading) } as Flow<ApolloResult<GetListQuery.Data?>>


}