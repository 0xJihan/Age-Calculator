package com.jihan.jetpack_instagram_clone.domain.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.jihan.jetpack_instagram_clone.model.AgeDetails
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit


fun calculateAgeDetails(birthDate: LocalDate, calculateToDate: LocalDate): AgeDetails {
    // Calculate the age in years, months, and days
    val period = Period.between(birthDate, calculateToDate)
    val years = period.years
    val months = period.months
    val days = period.days

    // Calculate the total months
    val totalMonths = years * 12 + months


    // Calculate the total days, weeks, hours, minutes, and seconds
    val totalDays = ChronoUnit.DAYS.between(birthDate, calculateToDate)
    val totalWeeks = totalDays / 7
    val totalHours = totalDays * 24
    val totalMinutes = totalHours * 60
    val totalSeconds = totalMinutes * 60

    return AgeDetails(
        years = years,
        months = months,
        days = days,
        totalMonths = totalMonths,
        totalWeeks = totalWeeks,
        totalDays = totalDays,
        totalHours = totalHours,
        totalMinutes = totalMinutes,
        totalSeconds = totalSeconds
    )
}


@Composable
fun RowScope.TabNavigationItem(tab: Tab, icon: BottomTabIcon) {

    val tabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current == tab

    val iconRes = if (isSelected) icon.selectedIcon else icon.unselectedIcon


    NavigationBarItem(selected = isSelected, onClick = {
        tabNavigator.current = tab
    }, label = {
        Text(tab.options.title)
    }, icon = {
        Icon(imageVector = iconRes, contentDescription = tab.options.title)
    }, colors = NavigationBarItemDefaults.colors(
        indicatorColor = MaterialTheme.colorScheme.primary,
    ), modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
    )


}


enum class BottomTabIcon(val selectedIcon: ImageVector, val unselectedIcon: ImageVector) {
    HOME(
        selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home
    ),

    LIST(
        selectedIcon = Icons.AutoMirrored.Filled.List,
        unselectedIcon = Icons.AutoMirrored.Outlined.List
    ),


}