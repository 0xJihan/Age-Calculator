package com.jihan.jetpack_instagram_clone.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface AgeDao {

    @Upsert
    fun insertAge(age: AgeEntity)

    @Delete
    fun deleteAge(age: AgeEntity)

    @Query("SELECT * FROM age order by id desc")
    fun getAge(): List<AgeEntity>

    @Query("SELECT * FROM age WHERE name LIKE :query")
    fun searchAges(query: String): List<AgeEntity>


}