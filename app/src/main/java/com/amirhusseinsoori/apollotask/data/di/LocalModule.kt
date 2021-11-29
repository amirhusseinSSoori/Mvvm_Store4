package com.amirhusseinsoori.apollotask.data.di

import android.content.Context
import androidx.room.Room
import com.amirhusseinsoori.apollotask.common.Constance.DbName
import com.amirhusseinsoori.apollotask.data.db.MyDataBase
import com.amirhusseinsoori.apollotask.data.db.dao.AccountDao
import com.amirhusseinsoori.apollotask.data.db.dao.GithubDao
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
    fun provideRepDAO(myDataBase: MyDataBase): GithubDao {
        return myDataBase.githubDao()
    }
    @Singleton
    @Provides
    fun provideGithubDAO(myDataBase: MyDataBase): AccountDao {
        return myDataBase.accountDao()
    }
}