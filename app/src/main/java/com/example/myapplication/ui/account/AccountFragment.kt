package com.example.myapplication.ui.account

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAccountBinding
import com.example.myapplication.util.setImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AccountFragment : Fragment(R.layout.fragment_account) {


    private val viewModel: AccountViewModel by viewModels()
    lateinit var binding: FragmentAccountBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setEvent(AccountContract.Event.EventProfile)
        sideEffect()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAccountBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)



        initObserve()

        binding!!.txtAccountProfileFShowMessage.setOnClickListener {
            viewModel.setEvent(AccountContract.Event.EventProfile)
        }

        binding.btnAccountFNavigation.setOnClickListener {
            findNavController().navigate(R.id.repositoryFragment)
        }


    }


    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it.state) {
                    is AccountContract.ProfileState.Idle -> Unit
                    is AccountContract.ProfileState.DetailsProfileState -> {
                        it.state.profile?.let { data ->
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
                        binding.progressBarAccount.isVisible = it.Active
                    }
                }
            }

        }
    }


}