package com.aslifitness.fitracker.model

/**
 * @author Shubham Pandey
 */
import androidx.annotation.Keep
import com.aslifitness.fitracker.detail.data.Workout

@Keep
data class WorkoutListResponse(val header: String? = null, val workoutList: List<Workout>? = null)