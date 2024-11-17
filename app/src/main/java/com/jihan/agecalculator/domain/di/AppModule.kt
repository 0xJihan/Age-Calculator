package com.jihan.agecalculator.domain.di

import android.content.Context
import androidx.room.Room
import com.jihan.agecalculator.domain.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule{

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }


    @Singleton
    @Provides
    fun providesAgeDao(appDatabase: AppDatabase) = appDatabase.ageDao()

}


