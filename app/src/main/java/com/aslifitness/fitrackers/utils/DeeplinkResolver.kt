package com.aslifitness.fitrackers.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri

/**
 * @author Shubham Pandey
 */
object DeeplinkResolver {

    fun resolve(activity: Activity, targetUrl: String?) {
        if (targetUrl.isNullOrEmpty()) return
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setPackage(activity.packageName)
            data = Uri.parse(targetUrl)
        }
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(intent)
        }
    }
}