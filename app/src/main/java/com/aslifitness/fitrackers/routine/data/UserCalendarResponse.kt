package com.aslifitness.fitrackers.routine.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
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
    @SerializedName("currentMonth") val currentMonth: MonthCalendar? = null,
    val prevMonths: List<MonthCalendar>? = null,
): Parcelable