package com.bassem.demo_task.ui

import android.view.View
import com.bassem.demo_task.data.models.Match
import com.bassem.demo_task.data.models.MatchItem
import com.bassem.demo_task.utils.DateUtils

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}
fun groupMatchesByDate(matches: List<Match>): List<MatchItem> {
    val groupedMatches = mutableListOf<MatchItem>()

    matches
        .sortedBy { DateUtils.formatIsoDateTime(it.utcDate) }  // Sort by date
        .groupBy { DateUtils.formatIsoDateTime(it.utcDate) }  // Group by formatted date
        .forEach { (date, matches) ->
            groupedMatches.add(MatchItem.DateHeader(date))
            matches.forEach { match -> groupedMatches.add(MatchItem.MatchData(match)) }
        }

    return groupedMatches
}