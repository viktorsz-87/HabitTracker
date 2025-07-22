package com.andronest.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andronest.model.entity.Habit
import com.andronest.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(private val repository: HabitRepository)
    : ViewModel() {

    val habits = repository.getAllHabits()

    fun addHabit(name: String) {
        viewModelScope.launch {
            val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow)
            val icons = listOf("🚀", "🏃", "📚", "💧")

            val habit = Habit(
                name = name,
                color = colors.random().toArgb(),
                icon = icons.random())

            repository.insertHabit(habit)
        }
    }

    fun removeHabit(habit: Habit)  {
        viewModelScope.launch{
            repository.deleteHabit(habit)
        }
    }
    fun completeHabit(habitId: Int)  {
        viewModelScope.launch {
            repository.completeHabit(habitId)
        }
    }
    fun getStreak(habitId: Int) = repository.getStreak(habitId)
}