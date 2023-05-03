package com.aslifitness.fitracker.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.aslifitness.fitracker.notification.FBNotificationService.Companion.NOTIFICATION_MESSAGE
import com.aslifitness.fitracker.notification.FBNotificationService.Companion.NOTIFICATION_TITLE


/**
 * Created by shubhampandey
 */

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NotificationUtil", "Notification received...")
        intent.let {
            val title = it.getStringExtra(NOTIFICATION_TITLE)
            val message = it.getStringExtra(NOTIFICATION_MESSAGE)
            if (!title.isNullOrEmpty() && !message.isNullOrEmpty()) {
                NotificationUtil(context).showNotification(title, message)
            }
        }
    }
}