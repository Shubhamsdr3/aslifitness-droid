package com.aslifitness.fitrackers.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import com.aslifitness.fitrackers.FitApp
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.utils.NavigationActivity
import java.util.*
import kotlin.random.Random

/**
 * Created by shubhampandey
 */
class NotificationUtil(private val context: Context) {

    companion object {
        private const val TAG = "NotificationUtil"
    }

    fun showNotification(notificationData: NotificationDto) {
        Log.d(TAG, "The notification data: $notificationData")
        val intent = Intent(context, NavigationActivity::class.java)
        intent.data = Uri.parse(notificationData.deeplinkUrl)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        // Create the TaskStackBuilder
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
        val channelId = context.getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setColor(ContextCompat.getColor(context, R.color.color_primary))
            .setSmallIcon(R.drawable.ic_dumble_new)
            .setContentTitle(notificationData.title)
            .setContentText(notificationData.message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setContentIntent(resultPendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Default Channel", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(Random.nextInt(), notificationBuilder.build())
    }

    fun scheduleAlarm(notificationDto: NotificationDto) {
        Log.d(TAG, "Scheduling notification...: ${notificationDto.scheduledTime}")
        FitApp.getAppContext()?.let { ctx ->
            val alarmMgr = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val bundle = Bundle().apply { putParcelable(FBNotificationService.NOTIFICATION_DATA, notificationDto) }
            val alarmIntent = Intent(ctx, ReminderReceiver::class.java).apply {
                putExtra(FBNotificationService.NOTIFICATION_DATA, bundle)
            }
            notificationDto.scheduledTime?.let { timeInMillis ->
                try {
                    Log.d(TAG,"Scheduled time: $timeInMillis")
                    val intent = PendingIntent.getBroadcast(ctx, timeInMillis.toInt(), alarmIntent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
                    alarmMgr.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, intent)
                } catch (ex: SecurityException) {
                    Log.d(TAG, ex.toString())
                }
            }
        }
    }
}