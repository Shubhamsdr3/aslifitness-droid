package com.aslifitness.fitracker.routine.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitracker.model.addworkout.NewAddWorkout
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class UserRoutineDto(
    val userId: String? = null,
    val title: String? = null,
    val workouts: List<NewAddWorkout>? = null
): Parcelable