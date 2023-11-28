package com.aslifitness.fitrackers.profile

import com.aslifitness.fitrackers.model.WorkoutDto

/**
 * @author Shubham Pandey
 */
interface DashboardAdapterCallback {

    fun onItemClicked(workoutDto: WorkoutDto)
}