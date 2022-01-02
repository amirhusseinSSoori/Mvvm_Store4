package com.amirhusseinsoori.apollotask.ui.repositories

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.amirhusseinsoori.apollotask.R

import com.amirhusseinsoori.apollotask.domain.model.NodeModel

import com.amirhusseinsoori.apollotask.databinding.FragmentRepositoryBinding
import com.amirhusseinsoori.apollotask.ui.base.BaseFragment
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
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.flowWithLifecycle(lifecycle,Lifecycle.State.STARTED).collect {
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
                        Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
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

    override fun onNavigationToAccount() {
        findNavController().navigate(R.id.action_repositoryFragment_to_accountFragment2)
    }


}