package com.amirhusseinsoori.apollotask.data.Rep

import com.apollographql.apollo.api.Response
import com.dropbox.android.external.store4.*
import com.amirhusseinsoori.apollotask.common.Constance
import com.amirhusseinsoori.apollotask.data.DispatcherProvider

import com.amirhusseinsoori.apollotask.data.db.entity.ProfileEntity

import com.amirhusseinsoori.apollotask.data.mappers.mapToProfile
import com.amirhusseinsoori.apollotask.data.mappers.mapToProfileModel
import com.amirhusseinsoori.apollotask.data.source.local.account.AccountLocalSource
import com.amirhusseinsoori.apollotask.data.source.remote.RemoteSource
import com.amirhusseinsoori.apollotask.domain.exption.Result
import com.amirhusseinsoori.apollotask.domain.model.NodeModel


import com.amirhusseinsoori.apollotask.domain.model.ProfileModel
import com.amirhusseinsoori.apollotask.domain.repository.ProfileRepositry


import example.myapplication.ProfileQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AccountRepositoryImp @Inject constructor(
    val network: RemoteSource,
    val local: AccountLocalSource,
    val dispatcher: DispatcherProvider
) : ProfileRepositry {

    override fun getStore(): Store<String, ProfileEntity> = StoreBuilder.from(
        fetcher = Fetcher.of { _: String ->
            network.getProfileFromNetwork()
        },
        sourceOfTruth = SourceOfTruth.Companion.of(
            reader = { local.getAccountRepository() },
            writer = { _, input: Response<ProfileQuery.Data> ->
                input.data?.let {
                    local.updateAccountRepository(it.mapToProfile())
                }
            }
        )
    ).build()


    override suspend fun getDetailsOfProfile(): Flow<Result<ProfileModel>> {
        return flow {
            getStore().stream(StoreRequest.cached(key = Constance.KeyAccount, refresh = true))
                .flowOn(dispatcher.io)
                .collect { response: StoreResponse<ProfileEntity> ->
                    when (response) {
                        is StoreResponse.Loading -> {
                            emit(Result.loading<ProfileModel>())
                        }
                        is StoreResponse.Error -> {
                            emit(Result.error<ProfileModel>(message = response.errorMessageOrNull()))
                        }
                        is StoreResponse.Data -> {
                            emit(Result.success(response.value.mapToProfileModel()))
                        }
                        is StoreResponse.NoNewData -> emit(Result.success(ProfileModel()))

                    }
                }
        }.flowOn(dispatcher.io)
    }
}