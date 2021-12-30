package com.amirhusseinsoori.apollotask.ui.repositories

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amirhusseinsoori.apollotask.R

import com.amirhusseinsoori.apollotask.domain.model.NodeModel

import com.amirhusseinsoori.apollotask.databinding.FragmentRepositoryBinding
import com.amirhusseinsoori.apollotask.ui.base.BaseFragment
import com.amirhusseinsoori.apollotask.ui.repositories.adabter.Controller
import com.amirhusseinsoori.apollotask.ui.repositories.adabter.MyController
import com.amirhusseinsoori.apollotask.ui.repositories.adabter.RepositoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoryFragment : BaseFragment<FragmentRepositoryBinding>(FragmentRepositoryBinding::inflate),RepositoryAdapter.Interaction {

    private val viewModel: RepositoryViewModel by viewModels()
    private lateinit var repositoryAdapter: RepositoryAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repositoryAdapter = RepositoryAdapter(this)
        viewModel.setEvent(RepositoryContract.Event.EventRepository)
        sideEffect()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()

        binding.txtRepositoryFShowMessage.setOnClickListener {
            viewModel.setEvent(RepositoryContract.Event.EventRepository)
        }

    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it.state) {
                    is RepositoryContract.RepositoriesState.Idle -> Unit
                    is RepositoryContract.RepositoriesState.AllRepositoriesState -> {
//                        setUpSeriesRecycler(list = it.state.repositories)
                        setupRecyclerView(list = it.state.repositories)
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

    private fun setupRecyclerView(list: List<NodeModel>) {
        val controller = Controller().apply {
            productItems = list
        }
        binding.recyclerviewRepositoryF.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewRepositoryF.setHasFixedSize(false)
        binding.recyclerviewRepositoryF.setController(controller)
    }

    override fun onNavigationToAccount() {
        findNavController().navigate(R.id.action_repositoryFragment_to_accountFragment2)
    }


}