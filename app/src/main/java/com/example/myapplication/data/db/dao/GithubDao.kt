package com.example.myapplication.data.db.dao

import androidx.room.*
import com.example.myapplication.data.db.entity.NodeEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface GithubDao {
    @Insert
    suspend fun insert(node: List<NodeEntity>)

    @Query("SELECT * FROM nodeEntity")
    fun getListRepository(): Flow<List<NodeEntity>>

    @Query("DELETE  FROM nodeEntity")
    suspend fun deleteAll()

    @Transaction
    suspend fun update(obj: List<NodeEntity>) {
        deleteAll()
        insert(obj)
    }
}