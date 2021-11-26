package com.example.myapplication.ui.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloQueryCall
import com.example.myapplication.data.network.errorHandle.ApolloResult
import com.example.myapplication.data.network.errorHandle.DataSourceException
import com.example.myapplication.data.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import example.myapplication.GetListQuery
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(val rep: Repository) : ViewModel() {
    val state = MutableStateFlow<ApolloResult<GetListQuery.Data?>>(ApolloResult.Empty())
    val _state = state.asStateFlow()


    fun showRepositoryList() {

        viewModelScope.launch {
            rep.getListRepFromSource("amirhusseinSSoori").collect {
                state.value= it
            }

        }

    }



}