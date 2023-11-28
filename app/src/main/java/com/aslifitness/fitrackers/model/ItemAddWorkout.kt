package com.aslifitness.fitrackers.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitrackers.detail.data.Workout
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */

@Keep
@Parcelize
data class ItemAddWorkout(val header: String? = null, val item: Workout? = null): Parcelable