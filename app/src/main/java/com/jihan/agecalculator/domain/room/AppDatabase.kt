package com.jihan.agecalculator.domain.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [AgeEntity::class], version = 1)
@TypeConverters(DatabaseTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ageDao(): AgeDao

}