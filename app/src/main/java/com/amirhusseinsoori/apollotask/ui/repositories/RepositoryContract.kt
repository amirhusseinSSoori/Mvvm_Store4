package com.amirhusseinsoori.apollotask.ui.repositories

import com.amirhusseinsoori.apollotask.common.Constance.EMPTY_STRING
import com.amirhusseinsoori.apollotask.domain.model.NodeModel
import com.amirhusseinsoori.apollotask.ui.base.UiEffect
import com.amirhusseinsoori.apollotask.ui.base.UiEvent
import com.amirhusseinsoori.apollotask.ui.base.UiState

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