package com.jihan.agecalculator.presentation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jihan.agecalculator.domain.model.AgeDetails
import com.jihan.agecalculator.domain.utils.toFormattedDate
import java.time.LocalDate

@Composable
fun AgeDetails(ageDetails: AgeDetails,start:LocalDate?=null) {
    // Display the calculated age details
    Text("Your age details:", fontSize = 25.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
    start?.let {
        Text(start.toFormattedDate(), fontSize =22.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.SemiBold)
    }
    Spacer(Modifier.height(15.dp))
    MyText(
        "Age",
        "${ageDetails.years} years ${ageDetails.months} months ${ageDetails.days} days"
    )
    MyText("Total Months", "${ageDetails.totalMonths} months")
    MyText("Total Weeks", "${ageDetails.totalWeeks} weeks")
    MyText("Total Days", "${ageDetails.totalDays} days")
    MyText("Total Hours", "${ageDetails.totalHours} hours")
    MyText("Total Minutes", "${ageDetails.totalMinutes} minutes")
    MyText("Total Seconds", "${ageDetails.totalSeconds} seconds")

    Spacer(Modifier.height(20.dp))
    Text(
        "Upcoming Birthdays:",
        fontSize = 25.sp,
        color = MaterialTheme.colorScheme.onSurface
    )



    ageDetails.nextBirthdays.map { birthday ->

        Text(
            text = birthday.year.toString(), fontSize = 30.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp)
        )

        Text(
            birthday.weekDay,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth(.9f)
        )
        MyText("Time Left", birthday.totalMonthsAndDaysLeft)
        MyText("Days Left", birthday.totalDaysLeft.toString())
        MyText("Hours Left", birthday.totalHoursLeft.toString())
        MyText("Minutes Left", birthday.totalMinutesLeft.toString())
        MyText("Seconds Left", birthday.totalSecondsLeft.toString())
        Spacer(Modifier.height(50.dp))
    }
}