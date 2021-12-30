package com.amirhusseinsoori.apollotask.ui.repositories.adabter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amirhusseinsoori.apollotask.R

import com.amirhusseinsoori.apollotask.domain.model.NodeModel
import com.amirhusseinsoori.apollotask.databinding.RepItemsBinding
import com.amirhusseinsoori.apollotask.ui.base.GlideApp
import com.amirhusseinsoori.apollotask.util.setImage
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import android.animation.ObjectAnimator

import com.bumptech.glide.request.transition.ViewPropertyTransition







class RepositoryAdapter(private val interaction: Interaction) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<NodeModel?>() {
        override fun areItemsTheSame(
            oldItem: NodeModel,
            newItem: NodeModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NodeModel,
            newItem: NodeModel
        ): Boolean {
            return oldItem.id == newItem.id
        }
    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RepItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, interaction,parent.context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.bind(differ.currentList[position]!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<NodeModel>) {
        differ.submitList(list)
    }

    class MovieViewHolder
    constructor(
        private val binding: RepItemsBinding,
        private val interaction: Interaction,
        val ctx:Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NodeModel) {
            itemView.setOnClickListener {
                interaction.onNavigationToAccount()
            }
            binding.apply {
                val animationObject =
                    ViewPropertyTransition.Animator { view ->
                        view.alpha = 0f
                        val fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
                        fadeAnim.duration = 2500
                        fadeAnim.start()
                    }
                val myOptions = RequestOptions()
                    .override(80, 80)
                GlideApp.with(imgItemRep.context)
                    .asBitmap()
                    .load(item.owner!!.avatarUrl!!)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(myOptions)
                    .transition(GenericTransitionOptions.with(animationObject))
                    .into(imgItemRep)

                txtItemRepName.text = item.name
                txtItemRepLogin.text = item.owner.login
                txtItemRepAvatar.text = item.owner.url
            }

        }
    }

    interface Interaction {
        fun onNavigationToAccount()
    }
}