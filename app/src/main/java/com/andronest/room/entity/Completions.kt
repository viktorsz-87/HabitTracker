package com.andronest.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "completions",
    foreignKeys = [ForeignKey(
        entity = Habit::class,
        parentColumns = ["id"],
        childColumns = ["habitId"],
        onDelete = ForeignKey.CASCADE)]
)
data class Completion(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val date: Long = 0, // Completion date
    val habitId: Int = 0, // Foreign key
)