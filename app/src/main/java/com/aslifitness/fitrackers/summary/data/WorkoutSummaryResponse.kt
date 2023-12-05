package com.aslifitness.fitrackers.summary.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Parcelize
@Keep
data class WorkoutSummaryResponse(
    val workoutId: String? = null,
    val data: WorkoutSummaryData? = null
): Parcelable

@Parcelize
@Keep
data class WorkoutSummaryData(
    val header: String? = null,
    val subHeader: String? = null,
    val summary: WorkoutSummary? = null
): Parcelable