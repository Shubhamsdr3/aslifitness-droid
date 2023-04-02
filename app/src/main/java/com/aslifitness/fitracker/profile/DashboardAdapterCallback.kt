package com.aslifitness.fitracker.profile

import com.aslifitness.fitracker.model.WorkoutDto

/**
 * @author Shubham Pandey
 */
interface DashboardAdapterCallback {

    fun onItemClicked(workoutDto: WorkoutDto)
}