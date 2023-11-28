package com.aslifitness.fitrackers.model

import android.os.Parcelable
import com.aslifitness.fitrackers.detail.data.Workout
import com.aslifitness.fitrackers.utils.Utility
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