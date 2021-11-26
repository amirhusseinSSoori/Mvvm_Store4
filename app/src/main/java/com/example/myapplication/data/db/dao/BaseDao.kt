package com.example.myapplication.data.db.dao

import androidx.room.Insert
import androidx.room.*

@Dao
interface BaseDao<T> {
    @Delete
    suspend fun insert(obj: List<T>)
}