package com.aslifitness.fitrackers

import android.app.Application
import android.content.Context
import com.aslifitness.fitrackers.sharedprefs.UserStore
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
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

        lateinit var cache: SimpleCache
    }

    private val cacheSize: Long = 90 * 1024 * 1024
    private lateinit var cacheEvictor: LeastRecentlyUsedCacheEvictor
    private lateinit var exoplayerDatabaseProvider: ExoDatabaseProvider

    override fun onCreate() {
        super.onCreate()
        initializeFirebaseAppCheck()
//        GlobalExceptionHandler.initialize(this, CrashActivity::class.java)
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
        init(applicationContext)

        cacheEvictor = LeastRecentlyUsedCacheEvictor(cacheSize)
        exoplayerDatabaseProvider = ExoDatabaseProvider(this)
        cache = SimpleCache(cacheDir, cacheEvictor, exoplayerDatabaseProvider)
    }

    private fun initializeFirebaseAppCheck() {
        FirebaseApp.initializeApp(this)
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance())
        FirebaseAuth.getInstance().addAuthStateListener {
            if (it.currentUser != null) {
                UserStore.setUserAuthenticated(true)
            } else {
                UserStore.setUserAuthenticated(false)
            }
        }
    }
}