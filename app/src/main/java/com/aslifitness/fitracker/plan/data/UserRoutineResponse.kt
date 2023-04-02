package com.aslifitness.fitracker.plan.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class UserRoutineResponse(
    val header: String? = null,
    val subHeader: String? = null,
    val currentPage: Int = 1,
    val currentMonth: MonthRoutine? = null,
    val prevMonths: List<MonthRoutine>? = null,
): Parcelable