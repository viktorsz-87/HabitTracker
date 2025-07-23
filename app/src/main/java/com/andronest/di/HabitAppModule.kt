package com.andronest.di

import android.content.Context
import com.andronest.repository.HabitRepository
import com.andronest.room.HabitDao
import com.andronest.room.HabitDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HabitAppModule{

    @Singleton
    @Provides
    fun provideHabitDatabase(@ApplicationContext context: Context): HabitDatabase {
        return HabitDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideHabitDao(habitDatabase: HabitDatabase): HabitDao {

        return habitDatabase.habitDao()
    }

    @Singleton
    @Provides
    fun provideHabitRepository(habitDao: HabitDao): HabitRepository {
        return HabitRepository(habitDao)
    }
}