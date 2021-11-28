package com.example.myapplication.ui.repositories

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAccountBinding

import com.example.myapplication.domain.model.NodeModel

import com.example.myapplication.databinding.FragmentRepositoryBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.repositories.adabter.RepositoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoryFragment : BaseFragment<FragmentRepositoryBinding>(FragmentRepositoryBinding::inflate) {

    private val viewModel: RepositoryViewModel by viewModels()
    private lateinit var repositoryAdapter: RepositoryAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repositoryAdapter = RepositoryAdapter()
        viewModel.setEvent(RepositoryContract.Event.EventRepository)
        sideEffect()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()

        binding!!.txtRepositoryFShowMessage.setOnClickListener {
            viewModel.setEvent(RepositoryContract.Event.EventRepository)
        }

    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it.state) {
                    is RepositoryContract.RepositoriesState.Idle -> Unit
                    is RepositoryContract.RepositoriesState.AllRepositoriesState -> {
                        setUpSeriesRecycler(list = it.state.repositories)
                    }
                }
            }
        }
    }


    private fun sideEffect() {
        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is RepositoryContract.Effect.ShowMessage -> {
                        binding!!.txtRepositoryFShowMessage.apply {
                            isVisible = it.Active
                            text = it.message
                        }
                    }
                    is RepositoryContract.Effect.ShowLoading -> {
                        binding!!.progressBarRepository.isVisible = it.Active
                    }
                }
            }

        }
    }


    private fun setUpSeriesRecycler(list: List<NodeModel>) {
        binding!!.recyclerviewRepositoryF.adapter = repositoryAdapter
        binding!!.recyclerviewRepositoryF.setHasFixedSize(true)
        repositoryAdapter?.submitList(list!!)
    }





}