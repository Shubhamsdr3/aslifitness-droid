package com.aslifitness.fitracker.addworkout

import com.aslifitness.fitracker.detail.data.Workout

/**
 * @author Shubham Pandey
 */
sealed class AddWorkoutState {

    data class AddNewSet(val position: Int, val workout: Workout) : AddWorkoutState()

    data class UpdateWeight(val weight: Int) : AddWorkoutState()

    data class UpdateSetCount(val setCount: Int): AddWorkoutState()
}