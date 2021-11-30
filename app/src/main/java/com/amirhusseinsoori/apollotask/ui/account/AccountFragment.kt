package com.amirhusseinsoori.apollotask.ui.account

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.amirhusseinsoori.apollotask.R
import com.amirhusseinsoori.apollotask.databinding.FragmentAccountBinding
import com.amirhusseinsoori.apollotask.ui.base.BaseFragment
import com.amirhusseinsoori.apollotask.util.setImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {


    private val viewModel: AccountViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setEvent(AccountContract.Event.EventProfile)
        sideEffect()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()


        binding.txtAccountProfileFShowMessage.setOnClickListener {
            viewModel.setEvent(AccountContract.Event.EventProfile)
        }

    }

    private fun initObserve() {
        lifecycleScope.launchWhenStarted {
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
        lifecycleScope.launch {
            viewModel.effect.collect {
                when (it) {
                    is AccountContract.Effect.ShowMessage -> {
                        binding.txtAccountProfileFShowMessage.apply {
                            isVisible = it.Active
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