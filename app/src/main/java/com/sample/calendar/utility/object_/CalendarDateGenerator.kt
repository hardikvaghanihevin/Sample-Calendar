package com.sample.calendar.utility.object_

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object CalendarDateGenerator {

    // Cached values to avoid reComputation
    private var cachedYearData: Map<Int, Map<Int, List<Int>>>? = null
    private var cachedYearMonthPairs: List<Pair<Int, Int>>? = null
    private var cachedStartYear: Int? = null
    private var cachedEndYear: Int? = null
    private var cachedIsZeroBased: Boolean? = null

    /** Generates both year data and (Year, Month) pairs. */
    suspend fun generateCalendar(startYear: Int, endYear: Int, isZeroBased: Boolean): Pair<Map<Int, Map<Int, List<Int>>>, List<Pair<Int, Int>>> {
        return withContext(Dispatchers.Default) {
            // Return cached data if already computed
            if (cachedYearData != null && cachedYearMonthPairs != null &&
                cachedStartYear == startYear && cachedEndYear == endYear && cachedIsZeroBased == isZeroBased) {
                return@withContext cachedYearData!! to cachedYearMonthPairs!!
            }

            // Generate new data
            val yearData = createYearData(startYear, endYear, isZeroBased)
            val yearMonthPairs = createYearMonthPairs(yearData)

            // Cache the result
            cachedYearData = yearData
            cachedYearMonthPairs = yearMonthPairs
            cachedStartYear = startYear
            cachedEndYear = endYear
            cachedIsZeroBased = isZeroBased

            yearData to yearMonthPairs
        }
    }

    /** Helper function: Creates year data with months and days */
    private fun createYearData(startYear: Int, endYear: Int, isZeroBased: Boolean): Map<Int, Map<Int, List<Int>>> {
        return (startYear..endYear).associateWith { year ->
            val monthsRange = if (isZeroBased) 0..11 else 1..12
            monthsRange.associateWith { month ->
                (1..getDaysInMonth(year, if (isZeroBased) month + 1 else month)).toList()
            }
        }
    }

    /** Helper function: Creates a list of (Year, Month) pairs */
    private fun createYearMonthPairs(yearData: Map<Int, Map<Int, List<Int>>>): List<Pair<Int, Int>> {
        return yearData.flatMap { (year, monthsMap) ->
            monthsMap.keys.map { month -> year to month }
        }
    }

    /** Returns the number of days in a given month */
    private fun getDaysInMonth(year: Int, month: Int): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear(year)) 29 else 28
            else -> throw IllegalArgumentException("Invalid month: $month")
        }
    }

    /** Determines if a year is a leap year */
    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
}

