package com.aslifitness.fitrackers.addworkout

import com.aslifitness.fitrackers.detail.data.Workout

/**
 * @author Shubham Pandey
 */
sealed class AddWorkoutState {

    data class AddNewSet(val position: Int, val workout: Workout) : AddWorkoutState()

    data class UpdateWeight(val weight: Int) : AddWorkoutState()

    data class UpdateSetCount(val setCount: Int): AddWorkoutState()
}