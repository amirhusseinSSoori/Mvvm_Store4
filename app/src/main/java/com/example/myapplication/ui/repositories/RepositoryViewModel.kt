package com.example.myapplication.ui.repositories

import android.util.Log
import android.view.View
import android.widget.Toast
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
                initListeners()
            }
            else -> Unit
        }
    }


    private fun initListeners() {
        viewModelScope.launch {
            showAllReposirtoryUseCase.execute().collect { result ->
                if (result.isSuccess()) {
                    if (result.data.isNullOrEmpty()) {
                        setEffect { ReposirtorContract.Effect.ShowLoading(false) }
                    } else {
                        setState { copy(state = ReposirtorContract.SendRequestState.Success(allData = result.data)) }
                        setEffect { ReposirtorContract.Effect.ShowLoading(false) }
                        setEffect {
                            ReposirtorContract.Effect.ShowMessage("Have Problem", false)
                        }
                    }
                } else if (result.isLoading()) {
                    setEffect { ReposirtorContract.Effect.ShowLoading(true) }
                } else {
                    setEffect {
                        ReposirtorContract.Effect.ShowMessage("Have Problem", true)
                    }
                    setEffect { ReposirtorContract.Effect.ShowLoading(false) }
                }
            }
        }
    }

}