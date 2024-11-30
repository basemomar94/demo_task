package com.bassem.demo_task.api

import com.bassem.demo_task.data.models.MatchesResult
import retrofit2.http.GET

interface ApiService {
    @GET("competitions/2021/matches")
    suspend fun getMatches(): MatchesResult
}



