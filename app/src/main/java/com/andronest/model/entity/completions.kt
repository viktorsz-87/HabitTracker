package com.andronest.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "completions")
data class HabitCompletion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val habitId: Int, // foreign key
    val date: Long = System.currentTimeMillis()
)