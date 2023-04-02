package com.aslifitness.fitracker.workoutlist

import com.aslifitness.fitracker.detail.data.Workout

/**
 * @author Shubham Pandey
 */
interface WorkoutListAdapterCallback {

    fun onItemClicked(position: Int, item: Workout)
}