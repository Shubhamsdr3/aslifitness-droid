package com.aslifitness.fitracker.model

import android.os.Parcelable
import androidx.annotation.Keep
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
    @SerializedName("user") val userDto: UserDto? = null,
    val items: List<WorkoutDto>? = null,
    val quotes: List<QuoteInfo>? = null
): Parcelable