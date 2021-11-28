package com.example.myapplication.data.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.common.Constance.DbName
import com.example.myapplication.data.db.MyDataBase
import com.example.myapplication.data.db.dao.GithubDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideMyDb(
        @ApplicationContext context: Context
    ): MyDataBase {
        return Room
            .databaseBuilder(
                context,
                MyDataBase::class.java,
                DbName
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMyDAO(myDataBase: MyDataBase): GithubDao {
        return myDataBase.githubDao()
    }
}