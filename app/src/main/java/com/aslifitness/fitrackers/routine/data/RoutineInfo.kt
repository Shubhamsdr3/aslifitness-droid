package com.aslifitness.fitrackers.routine.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitrackers.model.Reminder
import com.aslifitness.fitrackers.model.addworkout.WorkoutSetInfo
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class RoutineInfo(
    var reminder: Reminder? = null,
    var workoutSetInfo: WorkoutSetInfo? = null
): Parcelable