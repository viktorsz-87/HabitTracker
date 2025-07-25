package com.andronest.repository

import com.andronest.model.HabitWithCompletions
import com.andronest.room.HabitDao
import com.andronest.room.entity.Completion
import com.andronest.room.entity.Habit
import javax.inject.Inject

class HabitRepository @Inject constructor(
    private val habitDao: HabitDao
){
    suspend fun getHabitWithCompletions(habitId: Int): HabitWithCompletions?{
        return  habitDao.getHabitWithCompletions(habitId = habitId)
    }

    suspend fun getHabitsWithCompletions(): List<HabitWithCompletions>{
        return  habitDao.getHabitsWithCompletions()
    }

    suspend fun addHabit(habit: Habit){
        habitDao.insertHabit(habit = habit)
    }

    suspend fun removeHabit(habit: Habit){
        habitDao.deleteHabit(habit)
    }

    suspend fun completeHabit(habitId: Int){

        val completion = Completion(
            habitId = habitId,
            date = System.currentTimeMillis()
        )
        habitDao.insertCompletion(completion)
    }
}