package com.aslifitness.fitracker.summary.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Parcelize
@Keep
data class WorkoutSummaryResponse(
    val header: String? = null,
    val subHeader: String? = null,
    val summary: WorkoutSummary? = null
): Parcelable