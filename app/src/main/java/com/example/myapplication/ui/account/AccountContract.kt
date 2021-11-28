package com.example.myapplication.ui.account

import com.example.myapplication.common.Constance.EMPTY_STRING
import com.example.myapplication.domain.model.ProfileModel
import com.example.myapplication.ui.base.UiEffect
import com.example.myapplication.ui.base.UiEvent
import com.example.myapplication.ui.base.UiState

class AccountContract {

    sealed class Event : UiEvent {
        object EventProfile : Event()
    }

    data class State(
        val state: ProfileState
    ) : UiState

    sealed class ProfileState {
        object Idle : ProfileState()
        data class DetailsProfileState(val profile: ProfileModel) : ProfileState()
    }

    sealed class Effect : UiEffect {
        data class ShowMessage(val message: String =EMPTY_STRING, val isBoolean: Boolean = false) : Effect()
        data class ShowLoading(val Active: Boolean) : Effect()
    }

}