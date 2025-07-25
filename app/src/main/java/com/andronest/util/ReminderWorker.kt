package com.andronest.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andronest.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReminderWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams){

    override suspend fun doWork(): Result {
      try {
          showNotifications()
          return Result.success()

      } catch (e: Exception){
          return Result.failure()
      }
    }

    private suspend fun showNotifications() {

        withContext(Dispatchers.IO){
            val context = applicationContext
            val notificaitonManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val channel = NotificationChannel(
                "habit_reminders",
                "Habit Reminders",
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                description  ="Reminders to complete your habits"
            }
            notificaitonManager.createNotificationChannel(channel)

            val notification = NotificationCompat.Builder(context,"habit_reminders")
                .setContentTitle("Habit Reminder")
                .setContentText("Don't forget to complete your habits today!")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .build()

            notificaitonManager.notify(NOTIFICATION_ID, notification)
        }
    }

    companion object{
        const val NOTIFICATION_ID = 1001
        const val WORK_NAME =  "HabitReminderWork"
    }
}