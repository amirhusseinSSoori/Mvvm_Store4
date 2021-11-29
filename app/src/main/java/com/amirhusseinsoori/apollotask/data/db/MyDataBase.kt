package com.amirhusseinsoori.apollotask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amirhusseinsoori.apollotask.data.db.dao.AccountDao
import com.amirhusseinsoori.apollotask.data.db.dao.GithubDao
import com.amirhusseinsoori.apollotask.data.db.entity.NodeEntity
import com.amirhusseinsoori.apollotask.data.db.entity.ProfileEntity

@Database(
    entities = [NodeEntity::class, ProfileEntity::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class MyDataBase : RoomDatabase() {
    abstract fun githubDao(): GithubDao

    abstract fun accountDao(): AccountDao
}