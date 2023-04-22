package com.aslifitness.fitracker.widgets.addworkout

import com.aslifitness.fitracker.model.addworkout.WorkoutSetInfo

/**
 * Created by shubhampandey
 */
interface AddWorkoutWidgetCallback {

    fun onSetCompleted(workoutId: Int, setInfo: WorkoutSetInfo)
}