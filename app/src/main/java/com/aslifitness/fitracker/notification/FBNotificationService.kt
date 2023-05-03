package com.aslifitness.fitracker.notification

import android.util.Log
import com.aslifitness.fitracker.FitApp
import com.aslifitness.fitracker.firebase.FirestoreUtil
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by shubhampandey
 */
class FBNotificationService: FirebaseMessagingService() {

    companion object {
        private const val TAG = "FBNotificationService"
        const val NOTIFICATION_TITLE = "NOTIFICATION_TITLE"
        const val NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "Message received..: ${remoteMessage.data}")
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            val notificationDto = NotificationDto(
                title = remoteMessage.data["title"],
                message = remoteMessage.data["message"],
                isScheduled = remoteMessage.data["isScheduled"]?.toBoolean(),
                scheduledTime = remoteMessage.data["scheduledTime"]?.toLong()
            )
            configureNotification(notificationDto)
        }
    }

    private fun configureNotification(notificationDto: NotificationDto) {
        FitApp.getAppContext()?.let {
            if (!notificationDto.title.isNullOrEmpty() && !notificationDto.message.isNullOrEmpty()) {
                if (notificationDto.isScheduled == true) {
                    NotificationUtil(it).scheduleAlarm(notificationDto)
                } else {
                    NotificationUtil(it).showNotification(notificationDto.title, notificationDto.message)
                }
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG,"FCM token..: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            FirestoreUtil.getAndStoreRegToken()
        }
    }
}