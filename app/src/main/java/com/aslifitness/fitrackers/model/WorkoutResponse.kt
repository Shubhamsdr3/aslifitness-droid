package com.aslifitness.fitrackers.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitrackers.routine.data.UserRoutineDto
import com.aslifitness.fitrackers.summary.data.WorkoutSummary
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */
@Keep
@Parcelize
data class WorkoutResponse(
    val header: String? = null,
    val subHeader: String? = null,
    val workoutSummary: WorkoutSummary? = null,
    val routineSummary: UserRoutineDto? = null,
    @SerializedName("user") val userDto: UserDto? = null,
    val items: List<WorkoutDto>? = null,
    val quotes: List<QuoteInfo>? = null
): Parcelable