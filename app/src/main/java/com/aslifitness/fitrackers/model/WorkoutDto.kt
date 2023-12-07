package com.aslifitness.fitrackers.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */

@Keep
@Parcelize
data class WorkoutDto(val title: String? = null, val image: String? = null, val actionUrl: String? = null): Parcelable