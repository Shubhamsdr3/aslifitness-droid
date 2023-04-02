package com.aslifitness.fitracker.model

import android.os.Parcelable
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.utils.Utility
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */

@Parcelize
data class WorkoutSetData(
    val header: String? = null,
    var date: String = Utility.getCurrentTime(),
    var isExtended: Boolean? = null,
    val sets: MutableList<Workout>? = null
): Parcelable