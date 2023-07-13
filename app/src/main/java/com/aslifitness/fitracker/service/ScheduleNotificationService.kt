package com.aslifitness.fitracker.service

import android.app.IntentService
import android.content.Intent
import com.aslifitness.fitracker.db.AppDatabase

/**
 * Created by shubhampandey
 */
class ScheduleNotificationService(val name: String): IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        val routineDao = AppDatabase.getInstance().routineDao()

    }
}