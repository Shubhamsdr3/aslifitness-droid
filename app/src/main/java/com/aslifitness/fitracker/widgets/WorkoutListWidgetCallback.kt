package com.aslifitness.fitracker.widgets

import com.aslifitness.fitracker.detail.data.Workout

/**
 * @author Shubham Pandey
 */
interface WorkoutListWidgetCallback {

    fun onWorkoutSelected(workout: Workout)
}