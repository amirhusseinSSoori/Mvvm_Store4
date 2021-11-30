@file:Suppress("unused")
@file:JvmName("ElasticExtensions")
@file:JvmMultifileClass

package com.amirhusseinsoori.apollotask.util.elasticlayout

import android.view.View

@DslMarker
internal annotation class ElasticDsl

/**
 * An extension for operating elastic animation to the target view with custom attributes.
 *
 * @param scaleX The target elastic scale-x size of the animation.
 * @param scaleY The target elastic scale-y size of the animation.
 * @param duration The duration of the animation.
 * @param listener The [ElasticFinishListener] for being notified when the animation is finished.
 */
@ElasticDsl
@JvmOverloads
@JvmSynthetic
fun View.elasticAnimation(
  scaleX: Float = Definitions.DEFAULT_SCALE_X,
  scaleY: Float = Definitions.DEFAULT_SCALE_Y,
  duration: Int = Definitions.DEFAULT_DURATION,
  listener: ElasticFinishListener? = null
): ElasticAnimation {
  return ElasticAnimation(this)
    .setScaleX(scaleX)
    .setScaleY(scaleY)
    .setDuration(duration)
    .setOnFinishListener(listener)
}

/**
 * An extension for operating elastic animation to the target view with custom attributes.
 *
 * @param scaleX The target elastic scale-x size of the animation.
 * @param scaleY The target elastic scale-y size of the animation.
 * @param duration The duration of the animation.
 * @param block The lambda for being notified when the animation is finished.
 */
@ElasticDsl
@JvmOverloads
@JvmSynthetic
inline fun View.elasticAnimation(
  scaleX: Float = Definitions.DEFAULT_SCALE_X,
  scaleY: Float = Definitions.DEFAULT_SCALE_Y,
  duration: Int = Definitions.DEFAULT_DURATION,
  crossinline block: () -> Unit
): ElasticAnimation {
  return ElasticAnimation(this)
    .setScaleX(scaleX)
    .setScaleY(scaleY)
    .setDuration(duration)
    .setOnFinishListener(ElasticFinishListener { block() })
}

/**
 * An extension for creating elastic animation with kotlin dsl style.
 *
 * @param block The dsl block of the [ElasticAnimation].
 *
 * @return A new instance of the [ElasticAnimation].
 */
@ElasticDsl
@JvmSynthetic
inline fun elasticAnimation(
  view: View,
  crossinline block: ElasticAnimation.() -> Unit
): ElasticAnimation = ElasticAnimation(view).apply(block)
