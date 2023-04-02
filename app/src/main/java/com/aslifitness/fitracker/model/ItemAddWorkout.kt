package com.aslifitness.fitracker.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitracker.detail.data.Workout
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */

@Keep
@Parcelize
data class ItemAddWorkout(val header: String? = null, val item: Workout? = null): Parcelable