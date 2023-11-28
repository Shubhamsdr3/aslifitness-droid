package com.aslifitness.fitrackers.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.graphics.drawable.IconCompat
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.assistant.model.FitShortcutInfo

/**
 * Created by shubhampandey
 */
class ShortCutsFactory {

    inline fun <reified T> createShortCuts(context: Context, fitShortcutInfo: FitShortcutInfo): ShortcutInfoCompat {
        val addWorkoutIntent = Intent(context, T::class.java).apply {
            action = Intent.ACTION_VIEW
        }
        return ShortcutInfoCompat.Builder(context, fitShortcutInfo.shortCutId)
            .setIcon(IconCompat.createWithResource(context, R.drawable.ic_dumble_new))
            .setShortLabel(fitShortcutInfo.shortLabel)
            .setLongLabel(fitShortcutInfo.longLabel)
//            .addCapabilityBinding("aslifitness.actions.intent.ADD_WORKOUT", "exerciseName", listOf("\"$exerciseName\""))
            .setIntent(addWorkoutIntent)
            .build()
    }
}