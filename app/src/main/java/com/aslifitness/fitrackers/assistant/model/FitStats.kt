package com.aslifitness.fitrackers.assistant.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Entity that contains the total user stats.
 */
@Keep
@Parcelize
data class FitStats(val totalCount: Int, val totalDistanceMeters: Double, val totalDurationMs: Long): Parcelable