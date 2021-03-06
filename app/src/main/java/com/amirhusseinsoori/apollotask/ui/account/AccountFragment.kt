package com.amirhusseinsoori.apollotask.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
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
class AccountFragment : Fragment() {
    private val viewModel: AccountViewModel by viewModels()
    private var _dataBinding: FragmentAccountBinding? = null
    val dataBinding get() = _dataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        return _dataBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sideEffect()
        initObserve()
        dataBinding?.txtAccountProfileFShowMessage?.setOnClickListener {
            viewModel.setEvent(AccountContract.Event.EventProfile)
        }

    }

    private fun initObserve() {
       viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle,Lifecycle.State.CREATED).collect {
                when (it.state) {
                    is AccountContract.ProfileState.Idle -> Unit
                    is AccountContract.ProfileState.DetailsProfileState -> {
                        it.state.profile.let { data ->
                            dataBinding?.apply {
                                profile = data
                                Pair(data.avatarUrl!!, imgAccountProfileFShowImage).setImage()
                            }
                        }
                    }
                }
            }
        }

    }

    private fun sideEffect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effect.flowWithLifecycle(lifecycle,Lifecycle.State.CREATED).collect {
                when (it) {
                    is AccountContract.Effect.ShowMessage -> {
                        Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                    }
                    is AccountContract.Effect.ShowLoading -> {
                        dataBinding?.isLoading=it.Active
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _dataBinding = null
    }


}