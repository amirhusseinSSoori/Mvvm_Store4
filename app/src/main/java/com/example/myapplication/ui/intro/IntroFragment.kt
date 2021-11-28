package com.example.myapplication.ui.intro

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAccountBinding
import com.example.myapplication.databinding.FragmentIntroBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.util.explosion
import com.example.myapplication.util.startAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

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
            findNavController().navigate(R.id.action_introFragment_to_repositoryFragment)
        }



    
}
}