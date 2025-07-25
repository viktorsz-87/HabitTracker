package com.andronest.di

import android.app.Application
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class HabitTrackerApp : Application() {


    override fun onCreate() {
        super.onCreate()

        getNotificationManager(applicationContext)
    }
    fun getNotificationManager(context: Context) {

        // The id of the group.
        val groupId = "my_group_01"

        // The user-visible name of the group.
        val groupName = "my_name_01"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannelGroup(
            NotificationChannelGroup(groupId, groupName)
        )

    }

}