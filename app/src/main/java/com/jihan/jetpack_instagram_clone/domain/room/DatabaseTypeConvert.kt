package com.jihan.jetpack_instagram_clone.domain.room

import androidx.room.TypeConverter
import java.time.LocalDate

class DatabaseTypeConverter {

    @TypeConverter
    fun fromLocalDateToLong(date: LocalDate): Long {
        return date.toEpochDay()
    }

    @TypeConverter
    fun fromLongToLocalDate(value: Long): LocalDate {
        return LocalDate.ofEpochDay(value)
    }


}