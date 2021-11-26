package com.example.myapplication.data.repository

import android.util.Log
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.coroutines.await
import com.example.myapplication.data.db.enity.NodeEntity
import com.example.myapplication.data.db.enity.OwnerEntity


import com.example.myapplication.data.mappers.NodeMapper
import com.example.myapplication.data.mappers.NodeModel
import com.example.myapplication.data.mappers.mapToDomainModel
import com.example.myapplication.data.mappers.maptoEntityList
import com.example.myapplication.data.network.errorHandle.ApolloResult
import com.example.myapplication.data.network.errorHandle.DataSourceException
import com.example.myapplication.data.source.local.LocalSource
import com.example.myapplication.data.source.remote.RemoteSource
import example.myapplication.GetListQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    val network: RemoteSource,
    val local: LocalSource,
    val mapper: NodeMapper
) : Repository {
    override suspend fun getListRepFromSource(owner: String): Flow<ApolloResult<List<NodeModel>>> =
        flow {
            when (val result = network.getListRepFromNetwork(owner)) {
                is ApolloResult.Success -> result.data.apply {
                    result.data!!.repositoryOwner!!.repositories.nodes!!?.let {
                        local.insertListRepository(mapper.mapFromEntityList(it))
                    }
                    emit(ApolloResult.Success(this!!.mapToDomainModel()))
                }
                is ApolloResult.Error -> {
                    val listCharacters = local.getListRepository().first()
                    if (listCharacters.isNotEmpty()) {
                        emit(
                            ApolloResult.Success(
                                maptoEntityList(listCharacters)
                            )
                        )
                    } else {
                        emit(ApolloResult.Error(result.exception))
                    }

                   emit(ApolloResult.Error(Throwable()))
                }
                else -> Unit

            }
        }.onStart { emit(ApolloResult.Loading) }


}

