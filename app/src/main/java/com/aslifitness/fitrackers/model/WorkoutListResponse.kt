package com.aslifitness.fitrackers.model

/**
 * @author Shubham Pandey
 */
import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitrackers.detail.data.Workout
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class WorkoutListResponse(val header: String? = null, val workoutList: List<Workout>? = null): Parcelable