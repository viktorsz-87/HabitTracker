package com.andronest.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andronest.model.entity.Habit
import com.andronest.model.entity.HabitCompletion
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Habit::class)
    suspend fun insertHabit(habit: Habit)

    @Delete(entity = Habit::class)
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = HabitCompletion::class)
    suspend fun insertCompletion(completion: HabitCompletion)

    @Query("SELECT date from completions WHERE habitId = :habitId")
    fun getCompletionDate(habitId: Int): Flow<List<Long>>
}
