package com.bassem.demo_task.data.models


sealed class Result {
    data class Success(val result: MatchesResult) : Result()
    data class Fail(val reason: String) : Result()
    data object Loading : Result()
}