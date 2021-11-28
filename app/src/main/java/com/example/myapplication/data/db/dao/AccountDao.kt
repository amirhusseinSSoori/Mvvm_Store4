package com.example.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapplication.data.db.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface AccountDao {

    @Insert
    suspend fun insertProfile (profile: ProfileEntity)

    @Query("DELETE  FROM profileEntity")
    suspend fun delete()

    @Transaction
    suspend fun update(obj: ProfileEntity) {
        delete()
        insertProfile(obj)
    }

    @Query("SELECT * FROM profileEntity")
    fun getAccount(): Flow<ProfileEntity>
}