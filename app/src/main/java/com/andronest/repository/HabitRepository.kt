package com.andronest.repository

import com.andronest.model.entity.Habit
import com.andronest.model.entity.HabitCompletion
import com.andronest.room.HabitDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class HabitRepository @Inject constructor(private val habitDao: HabitDao) {

    suspend fun insertHabit(habit: Habit) = habitDao.insertHabit(habit)
    suspend fun deleteHabit(habit: Habit) = habitDao.deleteHabit(habit)
    fun getAllHabits() = habitDao.getAllHabits()

    suspend fun completeHabit(habitId: Int) = habitDao.insertCompletion(HabitCompletion(habitId = habitId))

   /* fun getStreak(habitId: Int): Flow<Int> = flow {
        val completions = habitDao.getCompletionDate(habitId).first()
        emit(calculateStreak(completions))
    } */

    fun getStreak(habitId: Int): Flow<Int> = flow {
        val completions = habitDao.getCompletionDate(habitId).map {
            emit(calculateStreak(it))
        }

    }

    private fun calculateStreak(dates: List<Long>): Int {

        val calendar = Calendar.getInstance()
        var streak = 0

        dates.sortedDescending().forEach { date->
            calendar.timeInMillis = date
            calendar.add(Calendar.DAY_OF_YEAR, -streak)
            if(calendar.timeInMillis <= System.currentTimeMillis()) streak++
        }
        return streak
    }
}