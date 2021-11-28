package com.example.myapplication.data.Rep


import com.apollographql.apollo.api.Response
import com.dropbox.android.external.store4.*
import com.example.myapplication.common.Constance.KeyStream
import com.example.myapplication.data.DispatcherProvider
import com.example.myapplication.data.db.entity.NodeEntity


import com.example.myapplication.data.mappers.*
import com.example.myapplication.data.source.local.repository.RepositoriesLocalSourceImp
import com.example.myapplication.data.source.remote.RemoteSource
import com.example.myapplication.domain.exption.SSOTResult
import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.domain.repository.Repository


import example.myapplication.GetListQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    val network: RemoteSource,
    val local: RepositoriesLocalSourceImp,
    val dispatcher: DispatcherProvider
) : Repository {
    // https://github.com/dropbox/Store
    override fun getStore(): Store<String, List<NodeEntity>> = StoreBuilder.from(
        fetcher = Fetcher.of { _: String ->
            network.getListRepFromNetwork()
        },
        sourceOfTruth = SourceOfTruth.Companion.of(
            reader = { local.getListRepository() },
            writer = { _, input: Response<GetListQuery.Data> ->
                input.data?.viewer?.repositories?.nodes?.let {
                    local.updateListRepository(it.mapListServerToEntity())
                }
            }
        )
    ).build()

    override suspend fun getLatestRepositories(): Flow<SSOTResult<List<NodeModel>>> {
        return flow {
            getStore().stream(StoreRequest.cached(key = KeyStream, refresh = true))
                .flowOn(dispatcher.io)
                .collect { response: StoreResponse<List<NodeEntity>> ->
                    when (response) {
                        is StoreResponse.Loading -> {
                            emit(SSOTResult.loading<List<NodeModel>>())
                        }
                        is StoreResponse.Error -> {
                            Exception()


                            emit(SSOTResult.error<List<NodeModel>>(msg = response.errorMessageOrNull()))
                        }
                        is StoreResponse.Data -> {
                            emit(SSOTResult.success(response.value.mapEntityListToModelList()))
                        }
                        is StoreResponse.NoNewData -> emit(SSOTResult.success(emptyList<NodeModel>()))
                    }
                }
        }.flowOn(dispatcher.io)
    }


}

