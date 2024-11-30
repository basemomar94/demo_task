package com.bassem.demo_task.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.bassem.demo_task.utils.AppConstants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "favorite_matches")
data class Match
    (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val awayTeam: AwayTeam? = null,
    val homeTeam: HomeTeam? = null,
    val lastUpdated: String? = "",
    val score: Score? = null,
    val status: String? = AppConstants.UNKNOWN,
    val utcDate: String? = ""
)

class MatchConverter {
    @TypeConverter
    fun fromStringToAwayTeam(value: String?): AwayTeam {
        val type = object : TypeToken<AwayTeam>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromAwayTeamToString(value: AwayTeam?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToHomeTeam(value: String?): HomeTeam {
        val type = object : TypeToken<HomeTeam>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromHomeTeamToString(value: HomeTeam?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToScore(value: String?): Score {
        val type = object : TypeToken<Score>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromScoreToString(value: Score?): String {
        return Gson().toJson(value)
    }
}