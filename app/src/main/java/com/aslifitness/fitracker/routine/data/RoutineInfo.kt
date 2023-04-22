package com.aslifitness.fitracker.routine.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitracker.model.Reminder
import com.aslifitness.fitracker.model.addworkout.WorkoutSetInfo
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