package com.aslifitness.fitrackers.workoutlist

import com.aslifitness.fitrackers.detail.data.Workout

/**
 * @author Shubham Pandey
 */
interface WorkoutListAdapterCallback {

    fun onItemClicked(position: Int, item: Workout)
}