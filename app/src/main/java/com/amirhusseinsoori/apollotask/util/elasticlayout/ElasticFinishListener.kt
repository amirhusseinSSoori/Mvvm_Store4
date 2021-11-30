package com.amirhusseinsoori.apollotask.util.elasticlayout

/** ElasticFinishListener is for listening elastic animation terminated status. */
fun interface ElasticFinishListener {

  /** invoked when the elastic animation is terminated. */
  fun onFinished()
}
