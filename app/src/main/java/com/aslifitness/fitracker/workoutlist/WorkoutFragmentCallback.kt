package com.aslifitness.fitracker.workoutlist

import com.aslifitness.fitracker.model.addworkout.NewAddWorkout

/**
 * @author Shubham Pandey
 */
interface WorkoutFragmentCallback {

    fun onWorkoutSelected(workoutList: List<NewAddWorkout>)
}