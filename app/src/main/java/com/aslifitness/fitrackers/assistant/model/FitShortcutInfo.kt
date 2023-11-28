package com.aslifitness.fitrackers.assistant.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class FitShortcutInfo(
    val shortCutId: String,
    val shortLabel: String,
    val longLabel: String,
    val parameterName: String,
    val parameters: List<String>,
    val intentAction: String
): Parcelable