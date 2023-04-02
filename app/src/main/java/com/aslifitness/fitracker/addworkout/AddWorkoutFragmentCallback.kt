package com.aslifitness.fitracker.addworkout

import com.aslifitness.fitracker.summary.data.WorkoutSummaryResponse

/**
 * Created by shubhampandey
 */
interface AddWorkoutFragmentCallback {

    fun onBackPressed()

    fun onWorkoutAdded(workoutSummary: WorkoutSummaryResponse)
}