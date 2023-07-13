package com.aslifitness.fitracker.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


/**
 * Created by shubhampandey
 */
class DeviceRebootReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals("android.intent.action.BOOT_COMPLETED")) {
            scheduleAlarm(context, intent)
        }
    }

    private fun scheduleAlarm(context: Context?, intent: Intent?) {
        val alarmIntent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0)
        val manager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval = 8000
        manager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            interval.toLong(),
            pendingIntent
        )
        Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show()
    }
}