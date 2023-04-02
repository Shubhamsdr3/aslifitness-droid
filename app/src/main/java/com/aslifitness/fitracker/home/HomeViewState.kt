package com.aslifitness.fitracker.home

import com.aslifitness.fitracker.model.WorkoutResponse

/**
 * @author Shubham Pandey
 */
sealed class HomeViewState {

    object ShowLoader: HomeViewState()

    object HideLoader: HomeViewState()

    data class ShowError(val throwable: Throwable): HomeViewState()

    data class ShowWorkoutList(val response: WorkoutResponse): HomeViewState()
}