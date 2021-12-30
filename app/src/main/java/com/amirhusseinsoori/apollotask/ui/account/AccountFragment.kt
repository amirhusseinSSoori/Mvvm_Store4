package com.amirhusseinsoori.apollotask.ui.account

import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.amirhusseinsoori.apollotask.R
import com.amirhusseinsoori.apollotask.databinding.FragmentAccountBinding
import com.amirhusseinsoori.apollotask.ui.base.GlideApp
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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
        viewModel.setEvent(AccountContract.Event.EventProfile)
        sideEffect()
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
        initObserve()
        dataBinding?.txtAccountProfileFShowMessage?.setOnClickListener {
            viewModel.setEvent(AccountContract.Event.EventProfile)
        }

    }

    private fun initObserve() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it.state) {
                    is AccountContract.ProfileState.Idle -> Unit
                    is AccountContract.ProfileState.DetailsProfileState -> {
                        it.state.profile.let { data ->
                            dataBinding?.apply {
                                profile = data

                                GlideApp.with(requireContext())
                                    .load(data.avatarUrl)
                                    .circleCrop()
                                    .transition(GenericTransitionOptions.with(R.anim.zoom_in))
                                    .into(imgAccountProfileFShowImage)

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
                        dataBinding!!.txtAccountProfileFShowMessage.apply {
                            isVisible = it.Active
                            text = it.message
                        }
                    }
                    is AccountContract.Effect.ShowLoading -> {
                        dataBinding?.isLoading=it.Active
                        //dataBinding?.progressBarAccount?.isVisible = it.Active
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