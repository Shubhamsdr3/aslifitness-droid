package com.aslifitness.fitracker.plan.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class DayRoutine(
    val type: String? = null,
    val date: Int = 1,
    val labels: List<Label>? = null
): Parcelable

@Keep
@Parcelize
data class Label(
    val name: String? = null,
    val color: String? = null
): Parcelable
