package com.aslifitness.fitracker.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.aslifitness.fitracker.FitApp
import com.aslifitness.fitracker.HomeActivity
import com.aslifitness.fitracker.R
import java.sql.Timestamp
import kotlin.random.Random

/**
 * Created by shubhampandey
 */
class NotificationUtil(private val context: Context) {

    companion object {
        private const val TAG = "NotificationUtil"
    }

    fun showNotification(title: String, message: String) {
        val intent = Intent(context, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = context.getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setColor(ContextCompat.getColor(context, R.color.color_primary))
            .setSmallIcon(R.drawable.ic_dumble_new)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

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
            val alarmIntent = Intent(ctx, ReminderReceiver::class.java).apply {
                putExtra(FBNotificationService.NOTIFICATION_TITLE, notificationDto.title)
                putExtra(FBNotificationService.NOTIFICATION_MESSAGE, notificationDto.message)
            }
            Log.d(TAG, "Scheduled time: ${notificationDto.scheduledTime}")
            notificationDto.scheduledTime?.let {
                val timeStamp = Timestamp(it)
                val intent = PendingIntent.getBroadcast(ctx, it.toInt(), alarmIntent, PendingIntent.FLAG_ONE_SHOT)
                alarmMgr.set(AlarmManager.RTC_WAKEUP, timeStamp.time, intent)
            }
        }
    }
}