package com.example.myapplication.ui.repositories

import com.example.myapplication.common.Constance.EMPTY_STRING
import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.ui.base.UiEffect
import com.example.myapplication.ui.base.UiEvent
import com.example.myapplication.ui.base.UiState

class RepositoryContract {

    sealed class Event : UiEvent {
        object EventRepository : Event()
    }

    data class State(
        val state: RepositoriesState
    ) : UiState

    sealed class RepositoriesState {
        object Idle : RepositoriesState()
        data class AllRepositoriesState(val repositories: List<NodeModel>) : RepositoriesState()
    }

    sealed class Effect : UiEffect {
        data class ShowMessage(val message: String = EMPTY_STRING, val Active: Boolean = false) : Effect()
        data class ShowLoading(val Active: Boolean) : Effect()
    }

}