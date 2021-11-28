package com.example.myapplication.domain.repository

import com.dropbox.android.external.store4.Store
import com.example.myapplication.data.db.entity.NodeEntity
import com.example.myapplication.data.db.entity.ProfileEntity
import com.example.myapplication.domain.exption.SSOTResult
import com.example.myapplication.domain.model.NodeModel
import com.example.myapplication.domain.model.ProfileModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepositry {

    suspend fun getDetailsOfProfile(): Flow<SSOTResult<ProfileModel>>
    fun getStore(): Store<String, ProfileEntity>
}