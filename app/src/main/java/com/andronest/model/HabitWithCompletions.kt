package com.andronest.model

import androidx.room.Embedded
import androidx.room.Relation
import com.andronest.room.entity.Completion
import com.andronest.room.entity.Habit

// Helper class
data class HabitWithCompletions(

    @Embedded
    val habit: Habit,

    @Relation(
        parentColumn = "id",
        entityColumn = "habitId"
    )

    val completion: List<Completion>
)