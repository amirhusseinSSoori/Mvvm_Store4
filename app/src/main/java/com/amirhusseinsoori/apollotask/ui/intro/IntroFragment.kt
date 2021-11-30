package com.amirhusseinsoori.apollotask.ui.intro

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.amirhusseinsoori.apollotask.R
import com.amirhusseinsoori.apollotask.databinding.FragmentIntroBinding
import com.amirhusseinsoori.apollotask.ui.base.BaseFragment
import com.amirhusseinsoori.apollotask.util.explosion
import com.amirhusseinsoori.apollotask.util.startAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class IntroFragment : BaseFragment<FragmentIntroBinding>(FragmentIntroBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            binding.apply {
                viewIntroFExplosion.let { explorer ->
                    explorer.isVisible = true
                    explorer.startAnimation(
                        Pair(
                            requireContext(),
                            R.anim.explosion_anim
                        ).explosion()
                    ) {
                        frameIntroFContainerAnim.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                        explorer.isVisible = false
                    }
                }
            }
            delay(3000)
            findNavController().navigate(R.id.action_introFragment_to_repositoryFragment2)
        }



    
}
}