package com.amirhusseinsoori.apollotask.domain.repository

import com.dropbox.android.external.store4.Store
import com.amirhusseinsoori.apollotask.data.db.entity.ProfileEntity
import com.amirhusseinsoori.apollotask.domain.exption.SSOTResult
import com.amirhusseinsoori.apollotask.domain.model.ProfileModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepositry {

    suspend fun getDetailsOfProfile(): Flow<SSOTResult<ProfileModel>>
    fun getStore(): Store<String, ProfileEntity>
}