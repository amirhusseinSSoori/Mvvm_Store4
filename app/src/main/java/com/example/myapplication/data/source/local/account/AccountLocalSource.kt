package com.example.myapplication.data.source.local.account

import com.example.myapplication.data.db.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface AccountLocalSource {
    fun getAccountRepository(): Flow<ProfileEntity>
    suspend fun updateAccountRepository(data: ProfileEntity)
}