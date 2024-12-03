package com.bassem.demo_task.viewmodel

import com.bassem.demo_task.data.models.Match
import com.bassem.demo_task.utils.AppConstants
import com.bassem.demo_task.viewmodels.MatchesViewModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@ExperimentalCoroutinesApi
class MatchesViewModelTest {
    private lateinit var matchesViewModel: MatchesViewModel


    @BeforeEach
    fun setup() {
        matchesViewModel = MatchesViewModel(mockk())
    }

    @MethodSource("provideFilter")
    @ParameterizedTest
    fun test_status_filters(
        expectedMatchesList: List<Match>,
        filter: String
    ) {


        val result = matchesViewModel.filterByStatus(mockMatchesList, filter)
        Assertions.assertEquals(expectedMatchesList, result)

    }

    companion object {

        private val finishedMatch = Match(
            status = AppConstants.FINISHED,
            id = 0,
            awayTeam = mockk(),
            homeTeam = mockk(),
            score = mockk(),
        )
        private val progressMatch = Match(
            status = AppConstants.MATCH_IN_PROGRESS,
            id = 0,
            awayTeam = mockk(),
            homeTeam = mockk(),
            score = mockk(),
        )
        private val scheduledMatch = Match(
            status = AppConstants.SCHEDULED,
            id = 0,
            awayTeam = mockk(),
            homeTeam = mockk(),
            score = mockk(),
        )
        private val halfTimeMatch = Match(
            status = AppConstants.HALF_TIME,
            id = 0,
            awayTeam = mockk(),
            homeTeam = mockk(),
            score = mockk(),
        )
        private val penaltiesMatch = Match(
            status = AppConstants.PENALTIES,
            id = 0,
            awayTeam = mockk(),
            homeTeam = mockk(),
            score = mockk(),
        )
        private val extraTimeMatch = Match(
            status = AppConstants.EXTRA_TIME,
            id = 0,
            awayTeam = mockk(),
            homeTeam = mockk(),
            score = mockk(),
        )
        private val fullTimeMatch = Match(
            status = AppConstants.FULL_TIME,
            id = 0,
            awayTeam = mockk(),
            homeTeam = mockk(),
            score = mockk(),
        )

        val mockMatchesList = arrayListOf(
            finishedMatch, scheduledMatch, halfTimeMatch,
            fullTimeMatch, extraTimeMatch, penaltiesMatch,
            progressMatch
        )

        @JvmStatic
        fun provideFilter(): Stream<Arguments> = Stream.of(
            Arguments.of(
                listOf(finishedMatch),
                AppConstants.FINISHED

            ),
            Arguments.of(
                listOf(scheduledMatch),
                AppConstants.SCHEDULED

            ),
            Arguments.of(
                listOf(halfTimeMatch),
                AppConstants.HALF_TIME

            ),
            Arguments.of(
                listOf(fullTimeMatch),
                AppConstants.FULL_TIME

            ),
            Arguments.of(
                listOf(extraTimeMatch),
                AppConstants.EXTRA_TIME

            ),
            Arguments.of(
                listOf(penaltiesMatch),
                AppConstants.PENALTIES

            ),
        )
    }
}