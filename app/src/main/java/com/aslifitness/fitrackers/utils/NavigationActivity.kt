package com.aslifitness.fitrackers.utils

import android.app.TaskStackBuilder
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitrackers.HomeActivity
import com.aslifitness.fitrackers.auth.UserAuthActivity
import com.aslifitness.fitrackers.sharedprefs.UserStore


/**
 * Created by shubhampandey
 */
class NavigationActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "NavigationActivity"
        private const val TYPE = "type"
        private const val INTERNAL_DEEPLINK = "app://aslifitness.com/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleDeeplink(intent.data)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleDeeplink(intent?.data)
    }

    private fun handleDeeplink(data: Uri?) {
        if (!UserStore.isUserAuthenticated()) {
            startActivity(Intent(this, UserAuthActivity::class.java))
            return
        }
        val type = data?.getQueryParameter(TYPE) ?: EMPTY
        val intent = Intent().apply { this.data = createDeeplink(type) }
        val taskStackBuilder = TaskStackBuilder.create(this)
        taskStackBuilder.addParentStack(HomeActivity::class.java)
        taskStackBuilder.addNextIntent(intent)
        try {
            taskStackBuilder.startActivities()
        } catch (ex: ActivityNotFoundException) {
            navigateToHome()
        }
        finish()
    }

    private fun createDeeplink(type: String): Uri {
        val deeplinkUrl = StringBuilder(INTERNAL_DEEPLINK).append(type).toString()
        return Uri.parse(deeplinkUrl)
    }

    override fun onBackPressed() {
        if (isTaskRoot) {
            val parentIntent = Intent(this, HomeActivity::class.java)
            startActivity(parentIntent)
            finish()
        } else {
            super.onBackPressed()
        }
    }

    private fun navigateToHome() {
        val parentIntent = Intent(this, HomeActivity::class.java)
        startActivity(parentIntent)
    }
}