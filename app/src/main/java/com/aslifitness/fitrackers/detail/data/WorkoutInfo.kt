package com.aslifitness.fitrackers.detail.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */

@Parcelize
@Keep
data class WorkoutInfo(val date: String? = null, val workout: String? = null, val data: List<Workout>? = null) : Parcelable
