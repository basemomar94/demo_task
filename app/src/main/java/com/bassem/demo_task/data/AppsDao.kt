package com.bassem.demo_task.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.bassem.demo_task.data.models.Match
import kotlinx.coroutines.flow.Flow

@Dao
interface AppsDao {
    @Upsert
    suspend fun addFavorite(favoriteMatch: Match)

    @Query("SELECT * FROM favorite_matches")
    fun getAllFavorites(): Flow<List<Match>>

    @Query("DELETE  FROM favorite_matches WHERE id=:id")
    suspend fun deleteFavoriteMatch(id: String)
}
