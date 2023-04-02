package com.aslifitness.fitracker.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */

@Parcelize
@Keep
data class WorkoutDto(val title: String? = null, val image: String? = null, val actionUrl: String? = null): Parcelable