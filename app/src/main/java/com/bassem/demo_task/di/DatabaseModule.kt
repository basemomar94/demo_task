package com.bassem.demo_task.di

import android.content.Context
import com.bassem.demo_task.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)

    @Provides
    fun provideAppsDao(appDatabase: AppDatabase) = appDatabase.appsDao()
}