package com.example.myapplication.ui.account

import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.Constance
import com.example.myapplication.common.Constance.Problem
import com.example.myapplication.domain.interactor.account.ShowAccountDetailsUseCase
import com.example.myapplication.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AccountViewModel @Inject constructor(private val showAccountDetailsUseCase: ShowAccountDetailsUseCase) :
    BaseViewModel<AccountContract.Event, AccountContract.State, AccountContract.Effect>() {
    override fun createInitialState(): AccountContract.State {
        return AccountContract.State(
            AccountContract.ProfileState.Idle
        )
    }

    override fun handleEvent(event: AccountContract.Event) {
        when (event) {
            is AccountContract.Event.EventProfile -> {
                showProfileRepository()
            }
        }
    }


    private fun showProfileRepository() {
        viewModelScope.launch {
            showAccountDetailsUseCase.execute().collect { result ->
                if (result.isSuccess()) {

                   
                    if (result.data == null) {
                        setEffect { AccountContract.Effect.ShowLoading(false) }
                    } else {
                        setState { copy(state = AccountContract.ProfileState.DetailsProfileState(profile = result.data)) }
                        setEffect { AccountContract.Effect.ShowLoading(false) }
                        setEffect {
                            AccountContract.Effect.ShowMessage(Problem, false)
                        }
                    }
                } else if (result.isLoading()) {
                    setEffect { AccountContract.Effect.ShowLoading(true) }
                } else {
                    setEffect {
                        AccountContract.Effect.ShowMessage(Problem, true)
                    }
                    setEffect { AccountContract.Effect.ShowLoading(false) }
                }
            }
        }
    }


}