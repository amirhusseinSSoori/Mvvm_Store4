package com.example.myapplication.ui.repositories

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.ResponseOrigin
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.example.myapplication.data.db.enity.NodeEntity
import com.example.myapplication.data.di.DispatcherProvider

import com.example.myapplication.domain.model.NodeModel


import com.example.myapplication.domain.useCase.allRepository.ShowAllRepositoryUseCase
import com.example.myapplication.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val showAllReposirtoryUseCase: ShowAllRepositoryUseCase,
    private val dispatcher: DispatcherProvider
) :
    BaseViewModel<ReposirtorContract.Event, ReposirtorContract.State, ReposirtorContract.Effect>() {


    override fun createInitialState(): ReposirtorContract.State {
        return ReposirtorContract.State(
            ReposirtorContract.SendRequestState.Idle
        )
    }

    override fun handleEvent(event: ReposirtorContract.Event) {
        when (event) {
            is ReposirtorContract.Event.OnShowResult -> {
                getLatestRep()
            }
            else -> Unit
        }
    }


    private  fun getLatestRep() {
        viewModelScope.launch {
            showAllReposirtoryUseCase.execute()
            .stream(StoreRequest.cached(key = "item", refresh = true))
            .flowOn(dispatcher.io())
            .collect { response: StoreResponse<List<NodeEntity>> ->
                when (response) {
                    is StoreResponse.Loading -> {
                        setEffect { ReposirtorContract.Effect.ShowLoading(true) }
                    }
                    is StoreResponse.Error -> {
                        if (response.origin == ResponseOrigin.Fetcher) setEffect { ReposirtorContract.Effect.ShowMessage(response.origin.name!!,true) }
                        setEffect { ReposirtorContract.Effect.ShowLoading(false) }
                    }
                    is StoreResponse.Data -> {

                        setState { copy(state = ReposirtorContract.SendRequestState.Success(allData = response.value)) }
                        setEffect { ReposirtorContract.Effect.ShowLoading(false) }
                        setEffect { ReposirtorContract.Effect.ShowMessage(response.origin.name!!,false) }
                    }


                    else -> Unit
                }
            }

        }
    }

}