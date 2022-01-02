package com.amirhusseinsoori.apollotask.ui.repositories

import androidx.lifecycle.viewModelScope
import com.amirhusseinsoori.apollotask.common.Constance
import com.amirhusseinsoori.apollotask.common.Constance.Problem
import com.amirhusseinsoori.apollotask.domain.interactor.repository.ShowAllRepositoryUseCase
import com.amirhusseinsoori.apollotask.ui.account.AccountContract


import com.amirhusseinsoori.apollotask.ui.base.BaseViewModel

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
    init {
        setEvent(RepositoryContract.Event.EventRepository)
    }

    override fun handleEvent(event: RepositoryContract.Event) {
        when (event) {
            is RepositoryContract.Event.EventRepository -> {
                showAllRepositories()
            }

        }
    }


    private fun showAllRepositories() {
        viewModelScope.launch {
            showAllRepository.execute().collect { result ->
                when {
                    result.isSuccess() -> {
                        result.data?.let {
                            setState {
                                copy(
                                    state = RepositoryContract.RepositoriesState.AllRepositoriesState(
                                        repositories = result.data
                                    )
                                )
                            }
                            setEffect { RepositoryContract.Effect.ShowLoading(false) }
                        } ?: run {
                            setEffect { RepositoryContract.Effect.ShowLoading(false) }
                        }
                    }
                    result.isLoading() -> {
                        setEffect { RepositoryContract.Effect.ShowLoading(true) }
                    }
                    result.isError() -> {
                        setEffect {
                            RepositoryContract.Effect.ShowMessage(message = result.message!!, true)
                        }
                        setEffect { RepositoryContract.Effect.ShowLoading(false) }
                    }
                }
            }

        }
    }
}

