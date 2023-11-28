package com.aslifitness.fitrackers.model.addworkout

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aslifitness.fitrackers.model.CtaInfo
import com.aslifitness.fitrackers.routine.data.RoutineInfo
import com.aslifitness.fitrackers.routine.data.RoutineWorkout
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "new_workout")
data class NewAddWorkout(

    @PrimaryKey
    val workoutId: Int = 0,

    @ColumnInfo("image")
    val image: String? = null,

    @ColumnInfo("title")
    val title: String? = null,

    @ColumnInfo("subtitle")
    val subTitle: String? = null,

    @ColumnInfo("routineInfo")
    var routineInfo: RoutineInfo? = null,

    @ColumnInfo("setInfo")
    var sets: List<WorkoutSetInfo>? = null,

    @ColumnInfo("addCta")
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