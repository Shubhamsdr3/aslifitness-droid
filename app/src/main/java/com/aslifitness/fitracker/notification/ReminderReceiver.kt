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

    companion object {
        private const val TAG = "NotificationUtil"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Notification received...")
        intent.let {
            val notificationData = intent.getBundleExtra(NOTIFICATION_DATA)?.getParcelable<NotificationDto>(NOTIFICATION_DATA)
            Log.d(TAG, "Notification received...$notificationData")
            notificationData?.run {
                NotificationUtil(context).showNotification(this)
            }
        }
    }
}