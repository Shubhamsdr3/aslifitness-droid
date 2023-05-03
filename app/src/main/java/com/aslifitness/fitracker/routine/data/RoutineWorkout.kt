package com.aslifitness.fitracker.routine.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class RoutineWorkout(
    val workoutId: Int?,
    val image: String? = null,
    val title: String? = null,
    val subTitle: String? = null,
    var routineInfo: RoutineInfo? = null
) : Parcelable