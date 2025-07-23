package com.andronest.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.andronest.model.HabitWithCompletions
import com.andronest.room.entity.Completion
import com.andronest.room.entity.Habit


@Dao
interface HabitDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletion(completion: Completion)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Transaction
    @Query("SELECT * FROM habits")
    suspend fun getHabitsWithCompletions(): List<HabitWithCompletions>

    @Transaction
    @Query("SELECT * FROM habits WHERE id = :habitId")
    suspend fun getHabitWithCompletions(habitId: Int): HabitWithCompletions?

}

