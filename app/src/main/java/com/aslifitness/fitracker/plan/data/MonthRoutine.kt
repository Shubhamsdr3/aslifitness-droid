package com.aslifitness.fitracker.plan.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class MonthRoutine(
    val month: Int? = null,
    val year: Int? = null,
    val numberOfDays: Int = 30,
    val routines: List<DayRoutine>? = null
): Parcelable