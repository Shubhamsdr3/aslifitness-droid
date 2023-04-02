package com.aslifitness.fitracker.detail.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */

@Parcelize
data class WorkoutInfo(val date: String? = null, val workout: String? = null, val data: List<Workout>? = null) : Parcelable
