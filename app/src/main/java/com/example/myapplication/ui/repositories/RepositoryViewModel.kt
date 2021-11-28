package com.example.myapplication.ui.repositories

import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.Constance.Problem
import com.example.myapplication.domain.interactor.repository.ShowAllRepositoryUseCase


import com.example.myapplication.ui.base.BaseViewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val showAllRepository: ShowAllRepositoryUseCase
) : BaseViewModel<ReposirtorContract.Event, ReposirtorContract.State, ReposirtorContract.Effect>() {


    override fun createInitialState(): ReposirtorContract.State {
        return ReposirtorContract.State(
            ReposirtorContract.RepositoriesState.Idle
        )
    }

    override fun handleEvent(event: ReposirtorContract.Event) {
        when (event) {
            is ReposirtorContract.Event.OnShowResult -> {
                showAllRepositories()
            }
            else -> Unit
        }
    }


    private fun showAllRepositories() {
        viewModelScope.launch {
            showAllRepository.execute().collect { result ->
                if (result.isSuccess()) {
                    if (result.data.isNullOrEmpty()) {
                        setEffect { ReposirtorContract.Effect.ShowLoading(false) }
                    } else {
                        setState { copy(state = ReposirtorContract.RepositoriesState.AllRepositoriesState(repositories = result.data)) }
                        setEffect { ReposirtorContract.Effect.ShowLoading(false) }
                        setEffect {
                            ReposirtorContract.Effect.ShowMessage(Problem, false)
                        }
                    }
                } else if (result.isLoading()) {
                    setEffect { ReposirtorContract.Effect.ShowLoading(true) }
                } else {
                    setEffect {
                        ReposirtorContract.Effect.ShowMessage(Problem, true)
                    }
                    setEffect { ReposirtorContract.Effect.ShowLoading(false) }
                }
            }
        }
    }

}