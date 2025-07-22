package com.andronest.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.andronest.R

class ReminderWorker(context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {
    override fun doWork(): Result {

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        NotificationChannel("habit_reminder", "Habit Reminder",
            NotificationManager.IMPORTANCE_DEFAULT).apply {
                notificationManager.createNotificationChannel(this)
            }

        NotificationCompat.Builder(applicationContext, "habit_reminder")
            .setContentTitle("Habit Tracker")
            .setContentText("Don't forget to complete your habits today!")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build().let { notificationManager.notify(1,it) }

        return Result.success()
    }
}