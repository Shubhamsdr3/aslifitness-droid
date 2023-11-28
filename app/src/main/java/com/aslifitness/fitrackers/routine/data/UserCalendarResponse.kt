package com.aslifitness.fitrackers.routine.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class UserCalendarResponse(
    val header: String? = null,
    val subHeader: String? = null,
    val currentPage: Int = 1,
    val currentMonth: MonthCalendar? = null,
    val prevMonths: List<MonthCalendar>? = null,
): Parcelable