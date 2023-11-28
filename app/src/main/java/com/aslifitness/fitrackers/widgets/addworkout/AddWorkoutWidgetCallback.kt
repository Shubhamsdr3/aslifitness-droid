package com.aslifitness.fitrackers.widgets.addworkout

import com.aslifitness.fitrackers.model.addworkout.WorkoutSetInfo

/**
 * Created by shubhampandey
 */
interface AddWorkoutWidgetCallback {

    fun onSetCompleted(workoutId: Int, setInfo: WorkoutSetInfo)
}