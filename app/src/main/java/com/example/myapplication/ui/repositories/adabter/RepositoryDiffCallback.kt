package com.example.myapplication.ui.repositories.adabter

import androidx.recyclerview.widget.DiffUtil
import example.myapplication.GetListQuery

class RepositoryDiffCallback: DiffUtil.ItemCallback<GetListQuery.Node?>() {
    override fun areItemsTheSame(oldItem: GetListQuery.Node, newItem: GetListQuery.Node) = oldItem.__typename == newItem.__typename
    override fun areContentsTheSame(oldItem: GetListQuery.Node, newItem: GetListQuery.Node) = oldItem == newItem }