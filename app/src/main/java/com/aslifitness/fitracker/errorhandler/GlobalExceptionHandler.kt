package com.aslifitness.fitracker.errorhandler

import android.content.Context
import android.content.Intent
import com.aslifitness.fitracker.utils.INTENT_DATA_NAME
import com.google.gson.Gson
import timber.log.Timber
import kotlin.system.exitProcess

/**
 * @author Shubham Pandey
 */
class GlobalExceptionHandler(
    private val applicationContext: Context,
    private val defaultHandler: Thread.UncaughtExceptionHandler,
    private val activityToBeLaunched: Class<*>
): Thread.UncaughtExceptionHandler {

    companion object {
        private const val TAG = "GlobalExceptionHandler"

        fun initialize(applicationContext: Context, activityToBeLaunched: Class<*>) {
            val handler = GlobalExceptionHandler(
                applicationContext,
                Thread.getDefaultUncaughtExceptionHandler() as Thread.UncaughtExceptionHandler,
                activityToBeLaunched
            )
            Thread.setDefaultUncaughtExceptionHandler(handler)
        }

        fun getThrowableFromIntent(intent: Intent): Throwable? {
            return try {
                Gson().fromJson(intent.getStringExtra(INTENT_DATA_NAME), Throwable::class.java)
            } catch (ex: Exception) {
                Timber.e(TAG, "getThrowableFromIntent", ex)
                null
            }
        }
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
//        try {
//            launchActivity(applicationContext, activityToBeLaunched, e)
//            exitProcess(0)
//        } catch (e: Exception) {
//            defaultHandler.uncaughtException(t, e)
//        }
    }

    private fun launchActivity(applicationContext: Context, activity: Class<*>, exception: Throwable) {
        Intent(applicationContext, activity).apply {
            putExtra(INTENT_DATA_NAME, Gson().toJson(exception))
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }.also {
           applicationContext.startActivity(it)
        }
    }
}