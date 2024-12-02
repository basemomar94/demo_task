package com.bassem.demo_task.utils

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {

    fun formatIsoDateTime(isoDateTime: String?): String {
        val inputFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME
        val outputFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("MM dd, yyyy", Locale.ENGLISH)
        val zonedDateTime = ZonedDateTime.parse(isoDateTime, inputFormatter)
        val localDate = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDate()
        return localDate.format(outputFormatter)
    }

    fun formatLocalDate(localDate: LocalDate?): String {
        return "From:" + localDate?.format(
            DateTimeFormatter.ofPattern(
                "EEE d MMM",
                Locale.ENGLISH
            )
        )
    }
}