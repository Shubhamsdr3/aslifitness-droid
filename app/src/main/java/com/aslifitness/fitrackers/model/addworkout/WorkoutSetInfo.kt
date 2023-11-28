package com.aslifitness.fitrackers.model.addworkout

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class WorkoutSetInfo(var weightInKg: Int = 0, var repsCount: Int = 0, var isDone: Boolean = false, var message: String? = null): Parcelable