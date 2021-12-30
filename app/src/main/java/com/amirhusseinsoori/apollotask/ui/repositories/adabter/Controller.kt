package com.amirhusseinsoori.apollotask.ui.repositories.adabter

import android.util.Log
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.amirhusseinsoori.apollotask.domain.model.NodeModel

import kotlin.properties.Delegates.observable

class Controller : EpoxyController() {
	init {
		Carousel.setDefaultGlobalSnapHelperFactory(null)
	}


	var productItems by observable(emptyList<NodeModel>()) { _, _, _ ->
		requestModelBuild()
	}

	override fun buildModels() {
		CarouselModel_()
			.id("PROMOTION_LIST")
			.numViewsToShowOnScreen(2.2f)
			.paddingDp(PADDING_IN_DP)
			.models(productItems.map { product ->
				ProductItemEpoxy_()
					.id(product.id)
					.product(product)
			})
			.addTo(this)
	}

	companion object {
		private const val PADDING_IN_DP = 8
	}
}