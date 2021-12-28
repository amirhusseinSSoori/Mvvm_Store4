package com.amirhusseinsoori.apollotask.ui.repositories.adabter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amirhusseinsoori.apollotask.databinding.RepItemsBinding
import com.amirhusseinsoori.apollotask.domain.model.NodeModel
import com.amirhusseinsoori.apollotask.util.setImage

class RepAdapter (private val interaction: Interaction) :
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
        return MovieViewHolder(binding, interaction)
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
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NodeModel) {
            itemView.setOnClickListener {
                interaction.onNavigationToAccount()
            }
            binding.apply {
                Pair(item.owner!!.avatarUrl!!, imgItemRep).setImage()
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