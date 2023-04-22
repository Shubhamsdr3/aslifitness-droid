package com.aslifitness.fitracker.widgets.calendar.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitracker.routine.data.Label
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */
@Keep
@Parcelize
data class CellInfo(val date: Int, val labels: List<Label>? = null): Parcelable