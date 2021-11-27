package com.example.myapplication.data.repository


import com.example.myapplication.data.mappers.*
import com.example.myapplication.domain.exception.ApolloResult
import com.example.myapplication.data.source.local.LocalSource
import com.example.myapplication.data.source.remote.RemoteSource
import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    val network: RemoteSource,
    val local: LocalSource,
    val mapper: NMapper
) : Repository {
    override suspend fun getListRepFromSource(): Flow<ApolloResult<List<NodeModel>>> =
        flow {
            when (val result = network.getListRepFromNetwork()) {
                is ApolloResult.Success -> result.data.apply {
                    result.data!!.viewer!!.repositories.nodes!!?.let {
                        local.updateListRepository(mapper.mapFromEntityList(it))
                    }
                    emit(ApolloResult.Success(this!!.mapToDomainModel()))
                }
                is ApolloResult.Error -> {
                    val repositoes = local.getListRepository().first()
                    if (repositoes.isNotEmpty()) {
                        emit(
                            ApolloResult.Success(
                                maptoEntityModel(repositoes)
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

