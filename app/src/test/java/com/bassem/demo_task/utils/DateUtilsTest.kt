package com.bassem.demo_task.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DateUtilsTest {


    @Test
    fun test_date_converter() {
        val expected = "Mon 26 Aug"
        val result = DateUtils.formatIsoDateTime("2024-08-26T05:21:21Z")
        assertEquals(expected, result)
    }
}