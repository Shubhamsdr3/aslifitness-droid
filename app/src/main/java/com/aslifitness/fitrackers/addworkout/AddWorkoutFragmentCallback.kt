package com.aslifitness.fitrackers.addworkout

import com.aslifitness.fitrackers.summary.data.WorkoutSummaryResponse

/**
 * Created by shubhampandey
 */
interface AddWorkoutFragmentCallback {

    fun onBackPressed()

    fun onWorkoutAdded(workoutSummary: WorkoutSummaryResponse)
}