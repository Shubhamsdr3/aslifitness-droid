package com.aslifitness.fitracker.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */
@Keep
@Parcelize
data class WorkoutResponse(
    val header: String? = null,
    val subHeader: String? = null,
    val userDto: UserDto? = null,
    val items: List<WorkoutDto>? = null): Parcelable