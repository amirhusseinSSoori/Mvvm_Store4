package com.amirhusseinsoori.apollotask.ui.repositories.adabter

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.Typed2EpoxyController
import com.amirhusseinsoori.apollotask.domain.model.NodeModel

class MyController : Typed2EpoxyController<List<NodeModel>, Boolean>() {
    override fun buildModels(data1: List<NodeModel>, data2: Boolean) {
        data1.forEachIndexed { index, s ->
            CarouselModel_()
                .id("PROMOTION_LIST")
                .numViewsToShowOnScreen(2.2f)
                .paddingDp(0)
                .models(data1.map { product ->
                    ProductItemEpoxy_()
                        .id(product.id)
                        .product(product)
                })
        }
    }
}