package com.example.myapplication.data.source.local

import com.example.myapplication.data.db.dao.GithubDao
import com.example.myapplication.data.db.enity.NodeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalSourceImp @Inject constructor(val dao: GithubDao) :LocalSource {
    override suspend fun getListRepository(): Flow<List<NodeEntity> >{
        return dao.getListRepository()
    }
    override suspend fun insertListRepository(list: List<NodeEntity>) {
        dao.insert(list)
    }
}

