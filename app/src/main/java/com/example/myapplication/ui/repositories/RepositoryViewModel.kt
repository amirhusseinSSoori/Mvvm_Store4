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
) : BaseViewModel<RepositoryContract.Event, RepositoryContract.State, RepositoryContract.Effect>() {


    override fun createInitialState(): RepositoryContract.State {
        return RepositoryContract.State(
            RepositoryContract.RepositoriesState.Idle
        )
    }

    override fun handleEvent(event: RepositoryContract.Event) {
        when (event) {
            is RepositoryContract.Event.EventRepository -> {
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
                        setEffect { RepositoryContract.Effect.ShowLoading(false) }
                    } else {
                        setState { copy(state = RepositoryContract.RepositoriesState.AllRepositoriesState(repositories = result.data)) }
                        setEffect { RepositoryContract.Effect.ShowLoading(false) }
                        setEffect {
                            RepositoryContract.Effect.ShowMessage(Problem, false)
                        }
                    }
                } else if (result.isLoading()) {
                    setEffect { RepositoryContract.Effect.ShowLoading(true) }
                } else {
                    setEffect {
                        RepositoryContract.Effect.ShowMessage(Problem, true)
                    }
                    setEffect { RepositoryContract.Effect.ShowLoading(false) }
                }
            }
        }
    }

}