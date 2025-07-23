package com.andronest.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "habits")
data class Habit(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val color: Int = 0,
    val name: String = "",

    val icon: String?,
)


