package com.example.myapplication.ui.repositories.adabter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.db.enity.NodeEntity

import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.databinding.RepItemsBinding

class RepositoryAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NodeEntity?>() {
        override fun areItemsTheSame(
            oldItem: NodeEntity,
            newItem: NodeEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: NodeEntity,
            newItem: NodeEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


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
    fun submitList(list: List<NodeEntity>) {
        differ.submitList(list)
    }

    class MovieViewHolder
    constructor(
        private val binding: RepItemsBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NodeEntity) = with(itemView) {
            binding.txtItemRep.text = item!!.name
            setOnClickListener {
                interaction?.onClicked(item)
            }
        }
    }

    interface Interaction {
        fun onClicked(item: NodeEntity)
    }
}