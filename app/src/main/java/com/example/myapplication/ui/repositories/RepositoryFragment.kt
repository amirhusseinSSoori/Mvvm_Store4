package com.example.myapplication.ui.repositories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R

import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.domain.exception.ApolloResult
import com.example.myapplication.databinding.FragmentRepositoryBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.repositories.adabter.RepositoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private val viewModel: RepositoryViewModel by viewModels()
    private lateinit var repositoryAdapter: RepositoryAdapter
     var binding: FragmentRepositoryBinding ?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repositoryAdapter = RepositoryAdapter()
        lifecycleScope.launchWhenResumed {
            viewModel.uiState.collect {
                when (it.state) {
                    is ReposirtorContract.SendRequestState.Idle -> {
                        Log.e("TAG", "onCollectTurnOnRequest:  isIdle")
                    }
                    is ReposirtorContract.SendRequestState.Loading -> {
                       binding!!.progressBarRepository.isVisible = it.state.isBoolean
                    }
                    is ReposirtorContract.SendRequestState.Success -> {
                        setUpSeriesRecycler(list = it.state.allData)
                    }
                }
            }
        }
        sideEffect()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRepositoryBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        viewModel.setEvent(ReposirtorContract.Event.OnShowResult)
        button()

    }




    private fun sideEffect() {
        lifecycleScope.launchWhenCreated {
            viewModel.effect.collect {
                when (it) {
                    is ReposirtorContract.Effect.ShowMessage -> {
//                        binding.txtRepositoryFShowMessage.isVisible = true
                    }
                }
            }

        }
    }

    fun button() {
        binding!!.txtRepositoryFShowMessage.setOnClickListener {
            findNavController().navigate(R.id.action_repositoryFragment_to_accountFragment)
//            viewModel.setEvent(ReposirtorContract.Event.OnShowResult)
        }
    }

    private fun setUpSeriesRecycler(list: List<NodeModel>) {
        binding!!.recyclerviewRepositoryF.adapter = repositoryAdapter
        binding!!.recyclerviewRepositoryF.setHasFixedSize(true)
        repositoryAdapter?.submitList(list!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }


}