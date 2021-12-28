package com.amirhusseinsoori.apollotask.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.amirhusseinsoori.apollotask.ui.base.loadImage
import com.google.android.material.internal.VisibilityAwareImageButton
import de.hdodenhof.circleimageview.CircleImageView

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