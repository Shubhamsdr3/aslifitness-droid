package com.aslifitness.fitrackers.widgets.addworkout

import com.aslifitness.fitrackers.model.addworkout.WorkoutSetInfo

/**
 * Created by shubhampandey
 */
interface AddSetCallback {

    fun onDoneClicked(setInfo: WorkoutSetInfo)
}