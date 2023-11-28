package com.aslifitness.fitrackers.detail.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitrackers.model.CtaInfo
import com.aslifitness.fitrackers.model.SetCountInfo
import com.aslifitness.fitrackers.model.addworkout.WorkoutSetInfo
import com.aslifitness.fitrackers.routine.data.RoutineInfo
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */

@Keep
@Parcelize
data class Workout(
    val workoutId: Int?,
    val image: String? = null,
    val header: String? = null,
    val subHeader: String? = null,
    val cta: CtaInfo? =  null,
    var isSelected: Boolean = false,
    val prevSets: List<WorkoutSetInfo>? = null,
    val qtyInfo: SetCountInfo? = null,
    val routineInfo: RoutineInfo? = null
): Parcelable