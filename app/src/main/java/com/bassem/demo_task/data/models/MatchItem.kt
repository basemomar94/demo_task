package com.bassem.demo_task.data.models

sealed class MatchItem {
    data class DateHeader(val date: String) : MatchItem()
    data class MatchData(val match: Match):MatchItem()
}