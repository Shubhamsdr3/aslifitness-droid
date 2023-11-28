package com.aslifitness.fitrackers.assistant.tracking

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.assistant.FitMainActivity
import com.aslifitness.fitrackers.assistant.model.FitActivity
import com.aslifitness.fitrackers.assistant.model.FitRepository

/**
 * Foreground Android Service that starts an activity and keep tracks of the status showing
 * a notification.
 */
class FitTrackingService : Service() {

    private companion object {
        private const val ONGOING_NOTIFICATION_ID = 999

        private const val CHANNEL_ID = "TrackingChannel"
    }

    private val fitRepository: FitRepository by lazy {
        FitRepository.getInstance(this)
    }

    /**
     * Create a notification builder that will be used to create and update the stats notification
     */
    private val notificationBuilder: NotificationCompat.Builder by lazy {
        val pendingIntent = Intent(this, FitMainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, 0)
        }
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getText(R.string.tracking_notification_title))
            .setSmallIcon(R.drawable.ic_run)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
    }

    /**
     * Observer that will update the notification with the ongoing activity status.
     */
    private val trackingObserver: Observer<FitActivity> = Observer { fitActivity ->
        fitActivity?.let {
            val km = String.format("%.2f", it.distanceMeters / 1000)
            val notification = notificationBuilder
                .setContentText(getString(R.string.stat_distance, km))
                .build()
            NotificationManagerCompat.from(this).notify(ONGOING_NOTIFICATION_ID, notification)
        }
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(ONGOING_NOTIFICATION_ID, notificationBuilder.build())
        fitRepository.startActivity()
        fitRepository.getOnGoingActivity().observeForever(trackingObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        fitRepository.stopActivity()
        fitRepository.getOnGoingActivity().removeObserver(trackingObserver)
    }

    /**
     * Creates a Notification channel needed for new version of Android
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            val notificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
