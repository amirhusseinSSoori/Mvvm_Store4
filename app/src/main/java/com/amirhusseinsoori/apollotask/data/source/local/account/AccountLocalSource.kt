package com.amirhusseinsoori.apollotask.data.source.local.account

import com.amirhusseinsoori.apollotask.data.db.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface AccountLocalSource {
    fun getAccountRepository(): Flow<ProfileEntity>
    suspend fun updateAccountRepository(data: ProfileEntity)
}