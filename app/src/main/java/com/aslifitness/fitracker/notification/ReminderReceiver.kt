package com.aslifitness.fitracker.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.aslifitness.fitracker.notification.FBNotificationService.Companion.NOTIFICATION_DATA


/**
 * Created by shubhampandey
 */

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NotificationUtil", "Notification received...")
        intent.let {
            val notificationData = intent.extras?.getParcelable<NotificationDto>(NOTIFICATION_DATA)
            notificationData?.run { NotificationUtil(context).showNotification(this) }
        }
    }
}