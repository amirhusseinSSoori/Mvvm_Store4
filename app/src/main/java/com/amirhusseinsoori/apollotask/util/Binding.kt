package com.amirhusseinsoori.apollotask.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.amirhusseinsoori.apollotask.ui.base.loadImage


object Binding {
    @BindingAdapter("visibi")
    @JvmStatic
    fun setVisibility(view: View, value: Boolean) {
        view.visibility = if (value) View.VISIBLE else View.GONE
    }

    @BindingAdapter("srcUrl")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?) {
        loadImage(imageUrl, view)
    }

}