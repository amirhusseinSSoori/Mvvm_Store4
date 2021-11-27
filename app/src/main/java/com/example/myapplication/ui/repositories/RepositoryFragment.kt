package com.example.myapplication.ui.repositories

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R

import com.example.myapplication.data.mappers.NodeModel
import com.example.myapplication.data.network.errorHandle.ApolloResult
import com.example.myapplication.databinding.FragmentRepositoryBinding
import com.example.myapplication.ui.BaseFragment
import com.example.myapplication.ui.repositories.adabter.RepositoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import example.myapplication.GetListQuery
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoryFragment : BaseFragment<FragmentRepositoryBinding>(FragmentRepositoryBinding::inflate) {

    private val viewModel: RepositoryViewModel by viewModels()
    private lateinit var repositoryAdapter: RepositoryAdapter

    override fun initialEvent() {
        viewModel.showRepositoryList()
    }

    override fun initObserve() {
        repositoryAdapter = RepositoryAdapter()
        lifecycleScope.launch {
            viewModel._state.collect {
                when(it){
                    is ApolloResult.Success ->{
                        setUpSeriesRecycler(it.data)
                        Log.e("TAG", "setUpSeriesRecycler: ${it.data}")
                    }
                    is ApolloResult.Error ->{
                        Log.e("TAG", "onCreate:${it.exception.cause} ", )
                    }
                    else -> Unit
                }
            }

        }
    }

    private fun setUpSeriesRecycler(list: List<NodeModel>) {
        binding.recyclerviewRepositoryF.adapter = repositoryAdapter
        binding.recyclerviewRepositoryF.setHasFixedSize(true)
        repositoryAdapter?.submitList(list!!)
    }



}