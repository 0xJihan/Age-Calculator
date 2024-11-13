package com.jihan.agecalculator.domain.model

import java.time.LocalDate


data class AgeDetails(
    val years: Int,
    val months: Int,
    val days: Int,
    val totalMonths: Long,
    val totalWeeks: Long,
    val totalDays: Long,
    val totalHours: Long,
    val totalMinutes: Long,
    val totalSeconds: Long,
    val nextBirthdays: List<BirthdayDetails>,
)


data class BirthdayDetails(
    val year: Int,
    val date: LocalDate,
    val totalMonthsAndDaysLeft: String,
    val totalDaysLeft: Long,
    val totalHoursLeft: Long,
    val totalMinutesLeft: Long,
    val totalSecondsLeft: Long,
    val weekDay: String,
)