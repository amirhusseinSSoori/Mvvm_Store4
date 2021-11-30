@file:Suppress("unused")

package com.amirhusseinsoori.apollotask.util.elasticlayout

import android.view.View
import android.view.ViewGroup
import android.view.animation.CycleInterpolator
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.amirhusseinsoori.apollotask.util.elasticlayout.Definitions.DEFAULT_ANIMATION_ANCHOR
import com.amirhusseinsoori.apollotask.util.elasticlayout.Definitions.DEFAULT_DURATION
import com.amirhusseinsoori.apollotask.util.elasticlayout.Definitions.DEFAULT_SCALE_X
import com.amirhusseinsoori.apollotask.util.elasticlayout.Definitions.DEFAULT_SCALE_Y


/** ElasticAnimation implements elastic animations for android views or view groups. */
class ElasticAnimation(private val view: View) {

  @JvmField
  @set:JvmSynthetic
  var scaleX = DEFAULT_SCALE_X

  @JvmField
  @set:JvmSynthetic
  var scaleY = DEFAULT_SCALE_Y

  @JvmField
  @set:JvmSynthetic
  var duration = DEFAULT_DURATION

  @JvmField
  @set:JvmSynthetic
  var listener: ViewPropertyAnimatorListener? = null

  @JvmField
  @set:JvmSynthetic
  var finishListener: ElasticFinishListener? = null

  var isAnimating: Boolean = false
    private set

  /** Sets a target elastic scale-x size of the animation. */
  fun setScaleX(scaleX: Float): ElasticAnimation = apply { this.scaleX = scaleX }

  /** Sets a target elastic scale-y size of the animation. */
  fun setScaleY(scaleY: Float): ElasticAnimation = apply { this.scaleY = scaleY }

  /** Sets a duration of the animation. */
  fun setDuration(duration: Int): ElasticAnimation = apply { this.duration = duration }

  /** Sets an animator listener of the animation. */
  fun setListener(listener: ViewPropertyAnimatorListener): ElasticAnimation = apply {
    this.listener = listener
  }

  /** An animator listener of the animation. */
  fun setOnFinishListener(finishListener: ElasticFinishListener?): ElasticAnimation = apply {
    this.finishListener = finishListener
  }

  /** An [ElasticFinishListener] listener of the animation. */
  @JvmSynthetic
  inline fun setOnFinishListener(crossinline block: () -> Unit): ElasticAnimation = apply {
    this.finishListener = ElasticFinishListener { block() }
  }

  /** starts elastic animation. */
  fun doAction() {
    if (!isAnimating && view.scaleX == 1f) {
      val animatorCompat = ViewCompat.animate(view)
        .setDuration(duration.toLong())
        .scaleX(scaleX)
        .scaleY(scaleY)
        .setInterpolator(CycleInterpolator(DEFAULT_ANIMATION_ANCHOR)).apply {
          listener?.let { setListener(it) } ?: setListener(object : ViewPropertyAnimatorListener {
            override fun onAnimationCancel(view: View?) = Unit
            override fun onAnimationStart(view: View?) {
              isAnimating = true
            }

            override fun onAnimationEnd(view: View?) {
              finishListener?.onFinished()
              isAnimating = false
            }
          })
        }
      if (view is ViewGroup) {
        (0 until view.childCount).map { view.getChildAt(it) }.forEach { child ->
          ViewCompat.animate(child)
            .setDuration(duration.toLong())
            .scaleX(scaleX)
            .scaleY(scaleY)
            .setInterpolator(CycleInterpolator(DEFAULT_ANIMATION_ANCHOR))
            .withLayer()
            .start()
        }
      }
      animatorCompat.withLayer().start()
    }
  }
}
