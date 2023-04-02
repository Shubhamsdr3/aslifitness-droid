package com.aslifitness.fitracker.widgets

import com.aslifitness.fitracker.detail.data.Workout

/**
 * @author Shubham Pandey
 */
interface AddWorkoutWidgetCallback {

    fun onPlusClicked(position: Int)

    fun onItemClicked(position: Int, workout: Workout)
}