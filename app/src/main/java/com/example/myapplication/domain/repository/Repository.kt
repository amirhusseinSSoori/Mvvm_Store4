package com.example.myapplication.domain.repository

import com.dropbox.android.external.store4.Store
import com.example.myapplication.data.db.enity.NodeEntity
import com.example.myapplication.domain.exption.MyResult
import kotlinx.coroutines.flow.Flow


interface Repository {

    suspend fun getLatestNews(): Flow<MyResult<List<NodeEntity>>>
}