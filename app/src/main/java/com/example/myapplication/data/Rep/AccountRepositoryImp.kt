package com.example.myapplication.data.Rep

import com.apollographql.apollo.api.Response
import com.dropbox.android.external.store4.*
import com.example.myapplication.common.Constance
import com.example.myapplication.data.DispatcherProvider

import com.example.myapplication.data.db.entity.ProfileEntity

import com.example.myapplication.data.mappers.mapToProfile
import com.example.myapplication.data.mappers.mapToProfileModel
import com.example.myapplication.data.source.local.account.AccountLocalSource
import com.example.myapplication.data.source.remote.RemoteSource
import com.example.myapplication.domain.exption.SSOTResult


import com.example.myapplication.domain.model.ProfileModel
import com.example.myapplication.domain.repository.ProfileRepositry


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


    override suspend fun getDetailsOfProfile(): Flow<SSOTResult<ProfileModel>> {
        return flow {
            getStore().stream(StoreRequest.cached(key = Constance.KeyAccount, refresh = true))
                .flowOn(dispatcher.io)
                .collect { response: StoreResponse<ProfileEntity> ->
                    when (response) {
                        is StoreResponse.Loading -> {
                            emit(SSOTResult.loading<ProfileModel>())
                        }
                        is StoreResponse.Error -> {
                            emit(SSOTResult.error<ProfileModel>(msg = response.errorMessageOrNull()))
                        }
                        is StoreResponse.Data -> {
                            emit(SSOTResult.success(response.value.mapToProfileModel()))
                        }
                        is StoreResponse.NoNewData -> {
                            emit(SSOTResult.error<ProfileModel>())
                        }

                    }
                }
        }.flowOn(dispatcher.io)
    }
}