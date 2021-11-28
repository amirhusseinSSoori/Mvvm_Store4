package com.example.myapplication.ui.repositories

import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.Constance.Problem


import com.example.myapplication.domain.useCase.allRepository.ShowAllRepositoryUseCase
import com.example.myapplication.ui.base.BaseViewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val showAllReposirtoryUseCase: ShowAllRepositoryUseCase
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