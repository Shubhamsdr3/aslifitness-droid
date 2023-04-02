package com.aslifitness.fitracker.home

import com.aslifitness.fitracker.model.WorkoutDto

/**
 * @author Shubham Pandey
 */
interface WorkOutAdapterCallback {

    fun onWorkoutSelected(workoutDto: WorkoutDto)
}