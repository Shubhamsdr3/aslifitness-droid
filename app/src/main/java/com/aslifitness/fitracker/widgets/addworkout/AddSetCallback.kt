package com.aslifitness.fitracker.widgets.addworkout

import com.aslifitness.fitracker.model.addworkout.WorkoutSetInfo

/**
 * Created by shubhampandey
 */
interface AddSetCallback {

    fun onDoneClicked(setInfo: WorkoutSetInfo)
}