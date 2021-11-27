package com.example.myapplication.domain.repository

import com.dropbox.android.external.store4.Store
import com.example.myapplication.data.db.enity.NodeEntity


interface Repository {
    fun getStore(): Store<String, List<NodeEntity>>
}