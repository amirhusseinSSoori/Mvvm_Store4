package com.example.myapplication.data.source.local.account

import com.example.myapplication.data.db.dao.AccountDao
import com.example.myapplication.data.db.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountImpLocalSource @Inject constructor(val dao :AccountDao):AccountLocalSource{
    override suspend fun updateAccountRepository(data: ProfileEntity) {
        return dao.update(data)
    }

    override fun getAccountRepository(): Flow<ProfileEntity> {
        return dao.getAccount()
    }
}