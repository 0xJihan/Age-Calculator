package com.jihan.jetpack_instagram_clone.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "age")
data class AgeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val description: String?=null,
    val start:LocalDate
)