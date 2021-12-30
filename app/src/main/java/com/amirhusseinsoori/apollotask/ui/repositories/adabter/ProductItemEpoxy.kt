package com.amirhusseinsoori.apollotask.ui.repositories.adabter

import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.amirhusseinsoori.apollotask.R
import com.amirhusseinsoori.apollotask.domain.model.NodeModel
import com.bumptech.glide.Glide


@EpoxyModelClass(layout = R.layout.rep_items)
abstract class ProductItemEpoxy : EpoxyModelWithHolder<Holder>() {

	@EpoxyAttribute lateinit var product: NodeModel

	override fun bind(holder: Holder) {
		with(holder) {
			Glide.with(this.productImage)
				.load(product.owner?.avatarUrl)
				.centerCrop()
				.into(this.productImage)

			this.productName.text = product.name
			this.productPrice.text = product.owner?.login
		}
	}
}

class Holder : KotlinEpoxyHolder() {
	val productImage by bind<ImageView>(R.id.img_itemRep)
	val productName by bind<TextView>(R.id.txt_itemRep_login)
	val productPrice by bind<TextView>(R.id.txt_itemRep_name)
}