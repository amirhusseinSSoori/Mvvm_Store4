package com.amirhusseinsoori.apollotask.ui.account

import androidx.lifecycle.viewModelScope
import com.amirhusseinsoori.apollotask.common.Constance.EMPTY_STRING
import com.amirhusseinsoori.apollotask.common.Constance.Problem
import com.amirhusseinsoori.apollotask.domain.interactor.account.ShowAccountDetailsUseCase
import com.amirhusseinsoori.apollotask.ui.base.BaseViewModel
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
                when {
                    result.isSuccess() -> {
                        result.data?.let {
                            setState {
                                copy(
                                    state = AccountContract.ProfileState.DetailsProfileState(
                                        profile = result.data
                                    )
                                )
                            }
                            setEffect { AccountContract.Effect.ShowLoading(false) }
                            setEffect {
                                AccountContract.Effect.ShowMessage(EMPTY_STRING, false)
                            }
                        } ?: run {
                            setEffect { AccountContract.Effect.ShowLoading(false) }
                        }
                    }
                    result.isLoading() -> {
                        setEffect { AccountContract.Effect.ShowLoading(true) }
                    }
                    else -> {
                        setEffect {
                            AccountContract.Effect.ShowMessage(Problem, true)
                        }
                        setEffect { AccountContract.Effect.ShowLoading(false) }
                    }
                }
            }
        }
    }


}