package com.aslifitness.fitracker.model.addworkout

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitracker.model.CtaInfo
import com.aslifitness.fitracker.model.Reminder
import com.aslifitness.fitracker.routine.data.RoutineInfo
import com.aslifitness.fitracker.routine.data.RoutineWorkout
import com.aslifitness.fitracker.routine.data.UserRoutineDto
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class NewAddWorkoutResponse(
    val userId: String? = null,
    val setData: List<NewAddWorkout>? = null
) : Parcelable

@Keep
@Parcelize
data class NewAddWorkout(
    val workoutId: Int? = null,
    val image: String? = null,
    val title: String? = null,
    val subTitle: String? = null,
    var routineInfo: RoutineInfo? = null,
    var sets: List<WorkoutSetInfo>? = null,
    val addSetCta: CtaInfo? = null,
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other !is NewAddWorkout) return false
        if (this !== other) return false
        if (this.workoutId == other.workoutId && this.title == other.title) return true
        return false
    }

    override fun hashCode(): Int {
        var result = workoutId ?: 0
        result = 31 * result + (image?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (subTitle?.hashCode() ?: 0)
        result = 31 * result + (sets?.hashCode() ?: 0)
        result = 31 * result + (addSetCta?.hashCode() ?: 0)
        return result
    }

    fun getRoutineWorkout(): RoutineWorkout {
        return RoutineWorkout(
            workoutId = workoutId,
            image = image,
            title = title,
            subTitle = subTitle
        )
    }
}
