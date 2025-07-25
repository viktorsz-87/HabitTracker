package com.andronest.util

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object NotificationScheduler {
    fun scheduleDailyReminder(context: Context, hour: Int, minute: Int){

        val workerManager = WorkManager.getInstance(context)
        workerManager.cancelUniqueWork(ReminderWorker.WORK_NAME)

        // Reminder at current HOUR, MIN
        val calendar = Calendar.getInstance().apply {

            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // Current reminder HH:MM <= SystemTime: add 1 day
            if(timeInMillis <= System.currentTimeMillis())
                add(Calendar.DAY_OF_YEAR, 1)
        }

        val initialDelay = calendar.timeInMillis - System.currentTimeMillis()

        // Constraints for worker
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(true)
            .build()

        // Create work request
        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS,
                TimeUnit.MILLISECONDS)
            .build()

        // Schedule work
        workerManager.enqueueUniquePeriodicWork(
            ReminderWorker.WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.UPDATE,
            request = workRequest,
        )
    }
}