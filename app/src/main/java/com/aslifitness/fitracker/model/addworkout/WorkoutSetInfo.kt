package com.aslifitness.fitracker.model.addworkout

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class WorkoutSetInfo(
    val column1: String? = null,
    val column2: String? = null,
    val column3: String? = null,
    var isDone: Boolean = false
): Parcelable