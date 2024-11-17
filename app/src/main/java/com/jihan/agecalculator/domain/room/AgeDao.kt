package com.jihan.agecalculator.domain.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AgeDao {

    @Upsert
    suspend fun insertAge(age: AgeEntity)

    @Delete
    suspend fun deleteAge(age: AgeEntity)

    @Query("SELECT * FROM age WHERE id = :id")
    suspend fun getAgeById(id: Int): AgeEntity


    @Query("SELECT * FROM age order by id desc")
     fun getAge(): Flow<List<AgeEntity>>

    @Query("SELECT * FROM age WHERE name LIKE :query")
    suspend fun searchAges(query: String): List<AgeEntity>


}