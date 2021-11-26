package com.example.myapplication.util
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<T : Any, VB: ViewBinding> constructor(
    private val interaction: Interaction<T>? = null
) : ListAdapter<T, BaseListAdapter.BaseViewHolder<VB,T>>(BaseDiffCallback<T>()) {


    override fun onBindViewHolder(holder: BaseViewHolder<VB,T>, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bindItem(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB,T> {
        return BaseViewHolder(inflateItemBinding(parent ,viewType),interaction)
    }

    override fun getItemCount() = currentList.size

    class BaseViewHolder<VB: ViewBinding,T>(
        private val binding: VB,
        private val interaction: Interaction<T>?
    ) : RecyclerView.ViewHolder(binding.root){
        fun bindItem(item: T)= with(binding) {
            root.setOnClickListener { interaction?.onItemClicked(item) }
        }

    }

    abstract fun inflateItemBinding(parent: ViewGroup, viewType: Int): VB

    interface Interaction<T> {
        fun onItemClicked(item: T)
    }

}