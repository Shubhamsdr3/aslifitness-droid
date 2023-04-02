package com.aslifitness.fitracker.model.addworkout

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class WorkoutSetInfo(var workoutId: Int, var weightInKg: Int = 0, var repsCount: Int = 0, var isDone: Boolean = false, var message: String? = null): Parcelable