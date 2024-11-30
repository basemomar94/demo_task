package com.bassem.demo_task.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bassem.demo_task.data.models.Match
import com.bassem.demo_task.data.models.MatchConverter
import com.bassem.demo_task.utils.AppConstants


@Database(entities = [Match::class], version = 2, exportSchema = false)
@TypeConverters(MatchConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appsDao(): AppsDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppConstants.DATA_BASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}
