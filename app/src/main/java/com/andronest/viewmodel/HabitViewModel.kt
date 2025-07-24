package com.andronest.viewmodel

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andronest.model.HabitUIState
import com.andronest.repository.HabitRepository
import com.andronest.room.entity.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HabitUIState>(HabitUIState())
    val uiState = _uiState.asStateFlow()

    init {
        getHabitsWithCompletions()
    }

    fun addHabit(name: String, color: Color, icon: String?="") {

        viewModelScope.launch {
            repository.addHabit(
                Habit(
                    color = color.toArgb(),
                    name = name,
                    icon = icon
                )
            )
        }
    }

    fun addCompletion(habitId: Int) {
        viewModelScope.launch {
            repository.completeHabit(habitId)
            //getHabitsWithCompletions()

            // Should be more efficient, needs to be tested
            _uiState.update { state ->
                state.copy(habits = state.habits.filter { it.habit.id != habitId })
            }
        }
    }

    fun getHabitWithCompletions(habitId: Int) {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, error = null, habits = emptyList()) }

            try {
                val result = repository.getHabitWithCompletions(habitId = habitId)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = if (result == null) "No data.." else null,
                        habits = if (result == null) emptyList() else listOf(result)
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message,
                        habits = emptyList()
                    )
                }
                Log.e("HabitError", e.message, e)
            }
        }
    }

    fun getHabitsWithCompletions() {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, error = null, habits = emptyList()) }

            try {
                val result = repository.getHabitsWithCompletions()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = if (result.isNullOrEmpty()) "No data.." else null,
                        habits = result
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message,
                        habits = emptyList()
                    )
                }
                Log.e("HabitError", e.message, e)
            }
        }
    }
}