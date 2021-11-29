package com.amirhusseinsoori.apollotask.data.source.local.repository

import com.amirhusseinsoori.apollotask.data.db.dao.GithubDao
import com.amirhusseinsoori.apollotask.data.db.entity.NodeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoriesLocalSourceImp @Inject constructor(val dao: GithubDao) :
    RepositoriesLocalSource {
    override fun getListRepository(): Flow<List<NodeEntity>> {
        return dao.getListRepository()
    }
    override suspend fun updateListRepository(list: List<NodeEntity>) {
        dao.update(list)
    }


}

