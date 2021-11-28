package com.example.myapplication.ui.repositories

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R

import com.example.myapplication.domain.model.NodeModel

import com.example.myapplication.databinding.FragmentRepositoryBinding
import com.example.myapplication.ui.repositories.adabter.RepositoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private val viewModel: RepositoryViewModel by viewModels()
    private lateinit var repositoryAdapter: RepositoryAdapter
    var binding: FragmentRepositoryBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repositoryAdapter = RepositoryAdapter()
        viewModel.setEvent(ReposirtorContract.Event.OnShowResult)
        sideEffect()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRepositoryBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        initObserve()

        binding!!.txtRepositoryFShowMessage.setOnClickListener {
            viewModel.setEvent(ReposirtorContract.Event.OnShowResult)
        }

    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it.state) {
                    is ReposirtorContract.SendRequestState.Idle -> Unit
                    is ReposirtorContract.SendRequestState.Success -> {
                        setUpSeriesRecycler(list = it.state.allData)
                    }
                }
            }
        }
    }


    private fun sideEffect() {
        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is ReposirtorContract.Effect.ShowMessage -> {
                        binding!!.txtRepositoryFShowMessage.apply {
                            isVisible = it.isBoolean
                            text = it.message
                        }
                    }
                    is ReposirtorContract.Effect.ShowLoading -> {
                        binding!!.progressBarRepository.isVisible = it.isBoolean
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


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}