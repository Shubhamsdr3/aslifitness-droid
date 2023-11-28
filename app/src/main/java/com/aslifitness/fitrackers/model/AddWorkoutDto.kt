package com.aslifitness.fitrackers.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitrackers.detail.data.WorkoutHistory
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */

@Keep
@Parcelize
data class AddWorkoutDto(val setData: WorkoutSetData? = null, val history: WorkoutHistory? = null, val cta: CtaInfo? = null): Parcelable