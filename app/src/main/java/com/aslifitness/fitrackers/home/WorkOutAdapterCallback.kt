package com.aslifitness.fitrackers.home

import com.aslifitness.fitrackers.model.WorkoutDto

/**
 * @author Shubham Pandey
 */
interface WorkOutAdapterCallback {

    fun onWorkoutSelected(workoutDto: WorkoutDto)
}