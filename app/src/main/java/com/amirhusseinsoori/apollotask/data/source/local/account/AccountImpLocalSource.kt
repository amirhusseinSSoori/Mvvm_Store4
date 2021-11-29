package com.amirhusseinsoori.apollotask.data.source.local.account

import com.amirhusseinsoori.apollotask.data.db.dao.AccountDao
import com.amirhusseinsoori.apollotask.data.db.entity.ProfileEntity
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