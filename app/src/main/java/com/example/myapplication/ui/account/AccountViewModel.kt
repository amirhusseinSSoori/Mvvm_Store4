package com.example.myapplication.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.Constance
import com.example.myapplication.domain.repository.ProfileRepositry
import com.example.myapplication.ui.base.BaseViewModel
import com.example.myapplication.ui.repositories.ReposirtorContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AccountViewModel  @Inject constructor(val rep: ProfileRepositry): BaseViewModel<AccountContract.Event, AccountContract.State, AccountContract.Effect>() {
    override fun createInitialState(): AccountContract.State {
        return AccountContract.State(
            AccountContract.SendRequestState.Idle
        )
    }

    override fun handleEvent(event: AccountContract.Event) {
        when (event) {
            is AccountContract.Event.OnShowResult -> {
                showAllRepositories()
            }
            else -> Unit
        }
    }



    private fun showAllRepositories() {
        viewModelScope.launch {
            rep.getLatestProfile().collect { result ->
                if (result.isSuccess()) {
                    if (result.data==null) {
                        setEffect { AccountContract.Effect.ShowLoading(false) }
                    } else {
                        setState { copy(state = AccountContract.SendRequestState.Success(allData = result.data)) }
                        setEffect { AccountContract.Effect.ShowLoading(false) }
                        setEffect {
                            AccountContract.Effect.ShowMessage(Constance.Problem, false)
                        }
                    }
                } else if (result.isLoading()) {
                    setEffect { AccountContract.Effect.ShowLoading(true) }
                } else {
                    setEffect {
                        AccountContract.Effect.ShowMessage(Constance.Problem, true)
                    }
                    setEffect { AccountContract.Effect.ShowLoading(false) }
                }
            }
        }
    }



}