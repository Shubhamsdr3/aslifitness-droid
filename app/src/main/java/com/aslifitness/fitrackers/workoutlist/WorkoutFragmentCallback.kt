package com.aslifitness.fitrackers.workoutlist

import com.aslifitness.fitrackers.model.addworkout.NewAddWorkout

/**
 * @author Shubham Pandey
 */
interface WorkoutFragmentCallback {

    fun onWorkoutSelected(workoutList: List<NewAddWorkout>)
}