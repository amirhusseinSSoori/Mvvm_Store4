package com.amirhusseinsoori.apollotask.util.elasticlayout

import android.view.View

/**
 * ElasticView is an interface for abstracting elastic view's listener.
 */
internal interface ElasticInterface {

  fun setOnClickListener(block: (View) -> Unit)

  fun setOnFinishListener(listener: ElasticFinishListener?)

  fun setOnFinishListener(block: () -> Unit)
}
