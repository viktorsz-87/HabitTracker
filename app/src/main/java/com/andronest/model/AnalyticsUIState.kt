package com.andronest.model

data class AnalyticsUIState(val isLoading: Boolean = false,
                            val error: String?="",
                            val habits:List<HabitWithCompletions> = emptyList())