package com.example.myapplication.data.Rep


import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.dropbox.android.external.store4.*
import com.example.myapplication.common.Constance.KeyStream
import com.example.myapplication.data.DispatcherProvider
import com.example.myapplication.data.db.enity.NodeEntity



import com.example.myapplication.data.mappers.*
import com.example.myapplication.data.source.local.LocalSource
import com.example.myapplication.data.source.remote.RemoteSource
import com.example.myapplication.domain.exption.MyResult
import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.domain.repository.Repository


import example.myapplication.GetListQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    val network: RemoteSource,
    val local: LocalSource,
    val dispatcher: DispatcherProvider
) : Repository {
     fun getStore(): Store<String, List<NodeEntity>> = StoreBuilder.from(
        fetcher = Fetcher.of { _: String ->
            network.getListRepFromNetwork()
        },
        sourceOfTruth = SourceOfTruth.Companion.of(
            reader = { local.getListRepository() },
            writer = { _, input: Response<GetListQuery.Data> ->
                local.updateListRepository(input.data!!.viewer.repositories.nodes!!.mapListServerToEntity())

            }
        )
    ).build()

    override suspend fun getLatestRepositories(): Flow<MyResult<List<NodeModel>>> {
        return flow {
            getStore().stream(StoreRequest.cached(key = KeyStream, refresh = true))
                .flowOn(dispatcher.io)
                .collect { response: StoreResponse<List<NodeEntity>> ->
                    when (response) {
                        is StoreResponse.Loading -> {
                            emit(MyResult.loading<List<NodeModel>>())
                        }
                        is StoreResponse.Error -> {
                            emit(MyResult.error<List<NodeModel>>())
                        }
                        is StoreResponse.Data -> {
                            emit(MyResult.success(response.value.mapEntityListToModelList()))
                        }
                        is StoreResponse.NoNewData -> emit(MyResult.success(emptyList<NodeModel>()))
                    }
                }
        }.flowOn(dispatcher.io)
    }





}

