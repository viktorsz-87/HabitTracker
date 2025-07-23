package com.andronest.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andronest.room.entity.Completion
import com.andronest.room.entity.Habit

@Database(entities = [Habit::class, Completion::class], version = 3)
abstract class HabitDatabase : RoomDatabase(){

    abstract fun habitDao(): HabitDao

    companion object{

        @Volatile
        var INSTANCE: HabitDatabase? = null

        fun getDatabase(context: Context): HabitDatabase {

            return INSTANCE?: synchronized(this){
                INSTANCE?: Room.databaseBuilder(
                    context,
                    klass = HabitDatabase::class.java,
                    name="habit-db")
                    .fallbackToDestructiveMigration(true)
                    .build().also { INSTANCE = it }
            }
        }
    }

}