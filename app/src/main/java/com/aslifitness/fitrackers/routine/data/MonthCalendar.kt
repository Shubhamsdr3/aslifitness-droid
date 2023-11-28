package com.aslifitness.fitrackers.routine.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class MonthCalendar(
    val month: Int? = null,
    val year: Int? = null,
    val numberOfDays: Int = 30,
    val routines: List<DayRoutine>? = null
): Parcelable