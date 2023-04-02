package com.aslifitness.fitracker

import android.app.Application
import android.content.Context
import com.aslifitness.fitracker.errorhandler.CrashActivity
import com.aslifitness.fitracker.errorhandler.GlobalExceptionHandler
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant
import java.lang.ref.WeakReference


/**
 * @author Shubham Pandey
 */

@HiltAndroidApp
class FitApp : Application() {

    companion object {
        private lateinit var contextWeakReference: WeakReference<Context>

        @JvmStatic
        fun init(context: Context) {
            this.contextWeakReference = WeakReference(context)
        }

        @JvmStatic
        fun getAppContext() = contextWeakReference.get()
    }

    override fun onCreate() {
        super.onCreate()
        initializeFirebaseAppCheck()
        GlobalExceptionHandler.initialize(this, CrashActivity::class.java)
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
        init(applicationContext)
    }

    private fun initializeFirebaseAppCheck() {
        FirebaseApp.initializeApp(this)
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance())
    }
}