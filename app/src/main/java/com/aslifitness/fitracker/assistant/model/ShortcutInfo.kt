package com.aslifitness.fitracker.assistant.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class ShortcutInfo(
    val shortLabel: String,
    val longLabel: String,
    val parameterName: String,
    val parameters: List<String>,
    val intentAction: String
): Parcelable