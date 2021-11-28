package com.example.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.data.db.dao.AccountDao
import com.example.myapplication.data.db.dao.GithubDao
import com.example.myapplication.data.db.entity.NodeEntity
import com.example.myapplication.data.db.entity.ProfileEntity

@Database(
    entities = [NodeEntity::class, ProfileEntity::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class MyDataBase : RoomDatabase() {
    abstract fun githubDao(): GithubDao

    abstract fun accountDao(): AccountDao
}