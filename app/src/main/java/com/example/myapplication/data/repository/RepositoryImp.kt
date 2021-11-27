package com.example.myapplication.data.repository


import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Response
import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.example.myapplication.data.db.enity.NodeEntity
import com.example.myapplication.data.mappers.*
import com.example.myapplication.data.source.local.LocalSource
import com.example.myapplication.data.source.remote.RemoteSource
import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.domain.repository.Repository
import example.myapplication.GetListQuery
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    val network: RemoteSource,
    val local: LocalSource,
    val mapper: NMapper
) : Repository {
    override fun getStore(): Store<String, List<NodeEntity>> = StoreBuilder.from(
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





}

