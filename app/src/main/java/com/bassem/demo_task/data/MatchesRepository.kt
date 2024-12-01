package com.bassem.demo_task.data

import com.bassem.demo_task.api.ApiService
import com.bassem.demo_task.data.models.Match
import com.bassem.demo_task.data.models.Result
import com.bassem.demo_task.utils.Logger
import com.bassem.demo_task.utils.Logger.e
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MatchesRepository @Inject constructor(
    private val service: ApiService,
    private val dao: AppsDao,
) {
    fun fetchMatches() = flow {
        emit(Result.Loading)
        try {
            val response = service.getMatches()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Fail(e.message ?: "Unknown Error"))
            Logger.e("error on fetchMatches ${e.message}")
        }

    }

    suspend fun makeFavorite(match: Match) {
        dao.addFavorite(match)
    }

    suspend fun getFavoriteMatches() = flow {
        emit(dao.getAllFavorites())
    }
}