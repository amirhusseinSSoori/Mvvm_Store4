package com.example.myapplication.ui.repositories

import com.example.myapplication.data.db.enity.NodeEntity
import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.ui.base.UiEffect
import com.example.myapplication.ui.base.UiEvent
import com.example.myapplication.ui.base.UiState

class ReposirtorContract {

    sealed class Event : UiEvent {

        object OnShowResult : Event()
        object HandelError : Event()
    }

    data class State(
        val state: SendRequestState
    ) : UiState

    sealed class SendRequestState {
        object Idle : SendRequestState()
        data class Success(val allData: List<NodeEntity>) : SendRequestState()
    }

    sealed class Effect : UiEffect {
        data class ShowMessage(val message: String,val isBoolean: Boolean=false) : Effect()
        data class ShowLoading(val isBoolean:Boolean) : Effect()
    }

}