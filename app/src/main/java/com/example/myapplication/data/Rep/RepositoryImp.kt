package com.example.myapplication.data.Rep


import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.dropbox.android.external.store4.*
import com.example.myapplication.data.db.enity.NodeEntity
import com.example.myapplication.data.di.DispatcherProvider
import com.example.myapplication.data.mappers.*
import com.example.myapplication.data.source.local.LocalSource
import com.example.myapplication.data.source.remote.RemoteSource
import com.example.myapplication.domain.exption.MyResult
import com.example.myapplication.domain.repository.Repository
import example.myapplication.GetListQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    val network: RemoteSource,
    val local: LocalSource,
    val mapper: NMapper,
     val dispatcher: DispatcherProvider
) : Repository {
     fun getStore(): Store<String, List<NodeEntity>> = StoreBuilder.from(
        fetcher = Fetcher.of { _: String ->
            network.getListRepFromNetwork()
        },
        sourceOfTruth = SourceOfTruth.Companion.of(
            reader = { local.getListRepository() },
            writer = { _, input: Response<GetListQuery.Data> ->
                local.updateListRepository(mapper.mapFromEntityList(input.data!!.viewer.repositories.nodes!!))

            }
        )
    ).build()

    override suspend fun getLatestNews(): Flow<MyResult<List<NodeEntity>>> {
        return flow {
            getStore().stream(StoreRequest.cached(key = "latest_news", refresh = true))
                .flowOn(dispatcher.io())
                .collect { response: StoreResponse<List<NodeEntity>> ->
                    when (response) {
                        is StoreResponse.Loading -> {
                            print("[Store 4] Loading from ${response.origin} \n")
                            emit(MyResult.loading<List<NodeEntity>>())
                        }
                        is StoreResponse.Error -> {
                            print("[Store 4] Error from  ${response.origin}  \n")
                            emit(MyResult.error<List<NodeEntity>>())
                        }
                        is StoreResponse.Data -> {
                            val data = response.value
                            print("[Store 4] Data from ${response.origin}  with ${response.value.size} elements \n")
                            emit(MyResult.success(data))
                        }
                        is StoreResponse.NoNewData -> emit(MyResult.success(emptyList<NodeEntity>()))
                    }
                }
        }.flowOn(dispatcher.io())
    }





}

