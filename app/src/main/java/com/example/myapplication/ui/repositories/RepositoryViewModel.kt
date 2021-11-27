package com.example.myapplication.ui.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.domain.exception.ApolloResult

import com.example.myapplication.domain.useCase.allRepository.ShowAllRepositoryUseCase
import com.example.myapplication.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(private val showAllReposirtoryUseCase: ShowAllRepositoryUseCase) :
    BaseViewModel<ReposirtorContract.Event, ReposirtorContract.State, ReposirtorContract.Effect>() {

    init {
        handleEvent(ReposirtorContract.Event.OnShowResult)
    }
    override fun createInitialState(): ReposirtorContract.State {
        return ReposirtorContract.State(
            ReposirtorContract.SendRequestState.Idle
        )
    }

    override fun handleEvent(event: ReposirtorContract.Event) {
        when (event) {
            is ReposirtorContract.Event.OnShowResult -> {
                showRepositoryList()
            }

            else -> Unit
        }
    }



    private fun showRepositoryList() {
        viewModelScope.launch {
            showAllReposirtoryUseCase.execute().collect { data ->
                when (data) {
                    is ApolloResult.Loading -> {
                        setState { copy(state = ReposirtorContract.SendRequestState.Loading(isBoolean = true)) }
                    }
                    is ApolloResult.Success -> {
                        setState { copy(state = ReposirtorContract.SendRequestState.Success(allData = data.data)) }
                        setState { copy(state = ReposirtorContract.SendRequestState.Loading(isBoolean = false)) }
                    }
                    is ApolloResult.Error -> {
                        setEffect { ReposirtorContract.Effect.ShowMessage(data.exception.cause.toString()!!) }
                        setState { copy(state = ReposirtorContract.SendRequestState.Loading(isBoolean = false)) }
                    }
                    else -> Unit
                }
            }
        }
    }
}