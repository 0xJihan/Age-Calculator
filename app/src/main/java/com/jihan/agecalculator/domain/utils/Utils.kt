package com.jihan.agecalculator.domain.utils

import androidx.compose.ui.graphics.vector.ImageVector
import com.jihan.agecalculator.domain.model.AgeDetails
import com.jihan.agecalculator.domain.model.BirthdayDetails
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit


fun calculateAgeDetails(birthDate: LocalDate, calculateToDate: LocalDate): AgeDetails {


    val period = Period.between(birthDate, calculateToDate)
    val years = period.years
    val months = period.months
    val days = period.days

    // Calculate total units using ChronoUnit
    val startDateTime = birthDate.atStartOfDay()
    val endDateTime = calculateToDate.atStartOfDay()

    val totalMonths = ChronoUnit.MONTHS.between(birthDate, calculateToDate)
    val totalDays = ChronoUnit.DAYS.between(birthDate, calculateToDate)
    val totalWeeks = ChronoUnit.WEEKS.between(birthDate, calculateToDate)
    val totalHours = ChronoUnit.HOURS.between(startDateTime, endDateTime)
    val totalMinutes = ChronoUnit.MINUTES.between(startDateTime, endDateTime)
    val totalSeconds = ChronoUnit.SECONDS.between(startDateTime, endDateTime)

    // Calculate details for the next 10 upcoming birthdays
    val nextBirthdays = mutableListOf<BirthdayDetails>()
    for (i in 1..10) {

        val nextBirthday = birthDate.withYear(calculateToDate.year + i)
        val nextBirthdayYear = calculateToDate.year + i
        val adjustedNextBirthday =
            if (nextBirthday.isBefore(calculateToDate)) nextBirthday.plusYears(1) else nextBirthday

        // Calculate the period until the next birthday
        val periodUntilBirthday = Period.between(calculateToDate, adjustedNextBirthday)
        val yearsLeft = periodUntilBirthday.years
        val monthsLeft = periodUntilBirthday.months
        val daysLeft = periodUntilBirthday.days
        val totalMonthsAndDaysLeft = "$yearsLeft years $monthsLeft months $daysLeft days"
        val weekDay = adjustedNextBirthday.dayOfWeek.name

        // Calculate total days, hours, minutes, and seconds left
        val totalDaysLeft = ChronoUnit.DAYS.between(calculateToDate, adjustedNextBirthday)
        val totalHoursLeft = ChronoUnit.HOURS.between(
            calculateToDate.atStartOfDay(), adjustedNextBirthday.atStartOfDay()
        )
        val totalMinutesLeft = ChronoUnit.MINUTES.between(
            calculateToDate.atStartOfDay(), adjustedNextBirthday.atStartOfDay()
        )
        val totalSecondsLeft = ChronoUnit.SECONDS.between(
            calculateToDate.atStartOfDay(), adjustedNextBirthday.atStartOfDay()
        )

        nextBirthdays.add(
            BirthdayDetails(
                year = nextBirthdayYear,
                date = adjustedNextBirthday,
                totalMonthsAndDaysLeft = totalMonthsAndDaysLeft,
                totalDaysLeft = totalDaysLeft,
                totalHoursLeft = totalHoursLeft,
                totalMinutesLeft = totalMinutesLeft,
                totalSecondsLeft = totalSecondsLeft,
                weekDay = weekDay
            )
        )
    }

    return AgeDetails(
        years = years,
        months = months,
        days = days,
        totalMonths = totalMonths,
        totalWeeks = totalWeeks,
        totalDays = totalDays,
        totalHours = totalHours,
        totalMinutes = totalMinutes,
        totalSeconds = totalSeconds,
        nextBirthdays = nextBirthdays.toList()
    )
}


data class BottomItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

