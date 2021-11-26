package com.example.myapplication.util

import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.myapplication.R


fun View.startAnimation(animation: Animation, onEnd:() -> Unit){
    animation.setAnimationListener(object :Animation.AnimationListener{
        override fun onAnimationStart(animation: Animation?) = Unit
        override fun onAnimationEnd(animation: Animation?) {
            onEnd()
        }
        override fun onAnimationRepeat(animation: Animation?) = Unit
    })
    this.startAnimation(animation)
}

fun Pair<Context,Int>.explosion():Animation{
    return AnimationUtils.loadAnimation(first, second).apply {
        duration = 3000
        interpolator = AccelerateDecelerateInterpolator()
    }

}