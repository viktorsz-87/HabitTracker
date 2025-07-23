package com.andronest.model

data class HabitUIState(val isLoading: Boolean = false,
                        val error: String?="",
                        val habits:List<HabitWithCompletions> = emptyList())
