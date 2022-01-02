package com.amirhusseinsoori.apollotask.util

import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


fun View.startAnimation(animation: Animation, onEnd: () -> Unit) {
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) = Unit
        override fun onAnimationEnd(animation: Animation?) {
            onEnd()
        }

        override fun onAnimationRepeat(animation: Animation?) = Unit
    })
    this.startAnimation(animation)
}

fun Pair<Context, Int>.explosion(): Animation {
    return AnimationUtils.loadAnimation(first, second).apply {
        duration = 3000
        interpolator = AccelerateDecelerateInterpolator()
    }

}

fun Pair<String,ImageView>.setImage() {
      Picasso.get().load(first).resize(400, 400).into(second)
}

private val COMMAND = "/system/bin/ping -c 1 8.8.8.8"
fun isConnectedToInternet(): Flow<Result<Boolean>> = flow {
    emit(Result.success(COMMAND.handlePing()))
}.catch { ex ->
    emit(kotlin.Result.failure(ex))
}.flowOn(Dispatchers.IO)
fun String.handlePing(): Boolean = Runtime.getRuntime().exec(this).waitFor() == 0