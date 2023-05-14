package com.aslifitness.fitracker.model.addworkout

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class NewAddWorkoutResponse(
    val userId: String? = null,
    val setData: List<NewAddWorkout>? = null
) : Parcelable