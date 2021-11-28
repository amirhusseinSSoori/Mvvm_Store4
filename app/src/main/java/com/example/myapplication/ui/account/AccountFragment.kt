package com.example.myapplication.ui.account

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAccountBinding
import com.example.myapplication.ui.repositories.ReposirtorContract
import com.example.myapplication.ui.repositories.RepositoryViewModel
import com.example.myapplication.util.setImage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AccountFragment : Fragment(R.layout.fragment_account) {


    private val viewModel: AccountViewModel by viewModels()
    lateinit var binding: FragmentAccountBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setEvent(AccountContract.Event.OnShowResult)
        sideEffect()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAccountBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)



        initObserve()



        binding.btnAccountFNavigation.setOnClickListener {
            findNavController().navigate(R.id.repositoryFragment)
        }


    }


    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it.state) {
                    is AccountContract.SendRequestState.Idle -> Unit
                    is AccountContract.SendRequestState.Success -> {

                        it.state.allData?.let { data ->
                            binding.apply {
                                Pair(
                                    data.avatarUrl!!,
                                    imgAccountProfileFShowImage
                                ).setImage()
                                txtAccountProfileFName.text = data.login
                                txtAccountProfileFUrl.text = data.url
                            }

                        }

                    }
                }
            }
        }
    }

    private fun sideEffect() {
        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is AccountContract.Effect.ShowMessage -> {
                        binding.txtAccountProfileFShowMessage.apply {
                            isVisible = it.isBoolean
                            text = it.message
                        }
                    }
                    is AccountContract.Effect.ShowLoading -> {
                        binding.progressBarAccount.isVisible = it.isBoolean
                    }
                }
            }

        }
    }


}