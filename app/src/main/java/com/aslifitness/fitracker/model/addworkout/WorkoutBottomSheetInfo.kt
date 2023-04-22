package com.aslifitness.fitracker.model.addworkout

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitracker.model.WorkoutDto
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class WorkoutBottomSheetInfo(
    val header: String? = null,
    val workout: WorkoutDto? = null,
    val routine: WorkoutDto? = null
) : Parcelable