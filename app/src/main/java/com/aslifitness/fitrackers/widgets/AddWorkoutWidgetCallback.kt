package com.aslifitness.fitrackers.widgets

import com.aslifitness.fitrackers.detail.data.Workout

/**
 * @author Shubham Pandey
 */
interface AddWorkoutWidgetCallback {

    fun onPlusClicked(position: Int)

    fun onItemClicked(position: Int, workout: Workout)
}