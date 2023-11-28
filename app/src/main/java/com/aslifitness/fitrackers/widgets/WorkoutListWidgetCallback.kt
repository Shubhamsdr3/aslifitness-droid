package com.aslifitness.fitrackers.widgets

import com.aslifitness.fitrackers.detail.data.Workout

/**
 * @author Shubham Pandey
 */
interface WorkoutListWidgetCallback {

    fun onWorkoutSelected(workout: Workout)
}