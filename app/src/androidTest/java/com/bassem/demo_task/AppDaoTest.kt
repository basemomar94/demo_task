package com.bassem.demo_task

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.bassem.demo_task.data.AppDatabase
import com.bassem.demo_task.data.AppsDao
import com.bassem.demo_task.data.models.AwayTeam
import com.bassem.demo_task.data.models.FullTime
import com.bassem.demo_task.data.models.HomeTeam
import kotlinx.coroutines.runBlocking
import com.bassem.demo_task.data.models.Match
import com.bassem.demo_task.data.models.Score
import com.bassem.demo_task.utils.AppConstants
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AppDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var appDao: AppsDao
    private val context: Context = ApplicationProvider.getApplicationContext()


    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()
        appDao = db.appsDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insert_read_from_local_db0() = runBlocking {
        val status = AppConstants.FULL_TIME
        val match = Match(
            id = 1,
            status = status,
            awayTeam = AwayTeam(id = 4, name = ""),
            homeTeam = HomeTeam(id = 4, name = ""),
            score = Score(duration = "", fullTime = FullTime(2, 2), winner = ""),

            )
        appDao.addFavorite(match)
        val apps = appDao.getAllFavorites().first()
        Assert.assertEquals(status, apps.first().status)

    }

}