package com.example.myapplication.ui.repositories

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.network.errorHandle.ApolloResult
import com.example.myapplication.databinding.FragmentRepositoryBinding
import com.example.myapplication.ui.repositories.adabter.RepositoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import example.myapplication.GetListQuery
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private val viewModel: RepositoryViewModel by viewModels()
    lateinit var binding: FragmentRepositoryBinding
    private lateinit var repositoryAdapter: RepositoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        repositoryAdapter = RepositoryAdapter()
        lifecycleScope.launch {
            viewModel.state.collect {

                when(it){
                    is ApolloResult.Success ->{
                        setUpSeriesRecycler(it.data!!.repositoryOwner!!.repositories.nodes!!)
                    }
                    is ApolloResult.Error ->{
                        Log.e("TAG", "onCreate:${it.exception.cause} ", )

                    }
                }








            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRepositoryBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        viewModel.showRepositoryList()

    }


    private fun setUpSeriesRecycler(list: List<GetListQuery.Node?>?) {
        Log.e("TAG", "setUpSeriesRecycler: ${list}")
        binding.recyclerviewRepositoryF.adapter = repositoryAdapter
        binding.recyclerviewRepositoryF.setHasFixedSize(true)
        repositoryAdapter?.submitList(list!!)
    }

}