package com.amirhusseinsoori.apollotask.ui.account

import com.amirhusseinsoori.apollotask.common.Constance.EMPTY_STRING
import com.amirhusseinsoori.apollotask.domain.model.ProfileModel
import com.amirhusseinsoori.apollotask.ui.base.UiEffect
import com.amirhusseinsoori.apollotask.ui.base.UiEvent
import com.amirhusseinsoori.apollotask.ui.base.UiState

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
        data class ShowMessage(val message: String =EMPTY_STRING, val Active: Boolean = false) : Effect()
        data class ShowLoading(val Active: Boolean) : Effect()
    }

}