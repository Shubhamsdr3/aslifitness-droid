package com.aslifitness.fitrackers.model

/**
 * @author Shubham Pandey
 */
import androidx.annotation.Keep
import com.aslifitness.fitrackers.detail.data.Workout

@Keep
data class WorkoutListResponse(val header: String? = null, val workoutList: List<Workout>? = null)