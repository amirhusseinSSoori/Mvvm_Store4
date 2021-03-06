package com.amirhusseinsoori.apollotask.data.repository


import com.apollographql.apollo.api.Response
import com.dropbox.android.external.store4.*
import com.amirhusseinsoori.apollotask.common.Constance.KeyStream
import com.amirhusseinsoori.apollotask.data.DispatcherProvider
import com.amirhusseinsoori.apollotask.data.db.entity.NodeEntity


import com.amirhusseinsoori.apollotask.data.mappers.*
import com.amirhusseinsoori.apollotask.data.source.local.repository.RepositoriesLocalSourceImp
import com.amirhusseinsoori.apollotask.data.source.remote.RemoteSource
import com.amirhusseinsoori.apollotask.domain.exption.Result
import com.amirhusseinsoori.apollotask.domain.model.NodeModel
import com.amirhusseinsoori.apollotask.domain.model.ProfileModel
import com.amirhusseinsoori.apollotask.domain.repository.Repository
import com.amirhusseinsoori.apollotask.util.isConnectedToInternet


import example.myapplication.GetListQuery
import kotlinx.coroutines.flow.*
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

    override suspend fun getLatestRepositories(): Flow<Result<List<NodeModel>>> {
        return flow {
            getStore().stream(StoreRequest.cached(key = KeyStream, refresh = true))
                .collect { response: StoreResponse<List<NodeEntity>> ->
                    when (response) {
                        is StoreResponse.Loading -> {
                            emit(Result.loading<List<NodeModel>>())
                        }
                        is StoreResponse.Error -> {
                            isConnectedToInternet().collect { network ->
                                network.fold(onSuccess = {
                                    if(it){
                                        emit(Result.error<List<NodeModel>>(message = response.errorMessageOrNull()))
                                    }else{
                                        emit(Result.error<List<NodeModel>>(message = "please check connection network"))
                                    } }, onFailure = {
                                    emit(Result.error<List<NodeModel>>(message = "google is done :)"))
                                    })
                            }
                        }
                        is StoreResponse.Data -> {
                            emit(Result.success(response.value.mapEntityListToModelList()))
                        }
                        is StoreResponse.NoNewData -> emit(Result.success(emptyList<NodeModel>()))
                    }
                }
        }.flowOn(dispatcher.io).cancellable()
    }


}

