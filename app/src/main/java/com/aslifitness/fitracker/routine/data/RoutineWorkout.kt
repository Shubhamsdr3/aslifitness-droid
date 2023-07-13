package com.aslifitness.fitracker.routine.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitracker.detail.data.Workout
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class RoutineWorkout(
    val workoutId: Int?,
    val image: String? = null,
    val title: String? = null,
    val subTitle: String? = null,
    var routineInfo: RoutineInfo? = null
) : Parcelable {

    fun getWorkout(): Workout {
        return Workout(
            workoutId = workoutId,
            image = image,
            header = title,
            subHeader = subTitle,
            routineInfo = routineInfo
        )
    }
}