package com.example.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.db.enity.NodeEntity
import kotlinx.coroutines.flow.Flow


@Dao
abstract class GithubDao :BaseDao<NodeEntity> {
    @Query("SELECT * FROM nodeEntity")
    abstract  fun getListRepository(): Flow<List<NodeEntity>>
}