package com.jihan.jetpack_instagram_clone.domain.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AgeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAge(age: AgeEntity)

    @Delete
    suspend fun deleteAge(age: AgeEntity)

    @Update
    suspend fun updateAge(age: AgeEntity)

    @Query("SELECT * FROM age order by id desc")
     fun getAge(): Flow<List<AgeEntity>>

    @Query("SELECT * FROM age WHERE name LIKE :query")
    suspend fun searchAges(query: String): List<AgeEntity>


}