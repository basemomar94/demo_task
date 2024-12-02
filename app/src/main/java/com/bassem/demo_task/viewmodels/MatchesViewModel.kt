package com.bassem.demo_task.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bassem.demo_task.data.MatchesRepository
import com.bassem.demo_task.data.models.Match
import com.bassem.demo_task.utils.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(private val matchesRepository: MatchesRepository) :
    ViewModel() {


    fun getMatches() = matchesRepository.fetchMatches()

    fun makeFavorite(match: Match) = viewModelScope.launch {
        matchesRepository.makeFavorite(match)
    }

    suspend fun getFavoriteMatches() = matchesRepository.getFavoriteMatches()

    fun filterByStatus(matches: List<Match>, filter: String): List<Match> {
        if (filter == AppConstants.ALL) return matches
        val filteredList = matches.filter { it.status == filter }
        return filteredList

    }

    fun filteredByFromToDate(matches: List<Match>, from: LocalDate? = null, to: LocalDate? = null): List<Match> {
        val isoFormatter = DateTimeFormatter.ISO_DATE_TIME

        return matches.filter { match ->
            val matchDate = match.utcDate?.let { ZonedDateTime.parse(it, isoFormatter).toLocalDate() }
            val isAfterFromDate = from?.let { matchDate?.isAfter(it) ?: false || matchDate?.isEqual(it) ?: false } ?: true
            val isBeforeToDate = to?.let { matchDate?.isBefore(it) ?: false || matchDate?.isEqual(it) ?: false } ?: true
            isAfterFromDate && isBeforeToDate
        }
    }


}