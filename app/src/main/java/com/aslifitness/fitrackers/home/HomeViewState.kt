package com.aslifitness.fitrackers.home

import com.aslifitness.fitrackers.model.QuoteInfo
import com.aslifitness.fitrackers.routine.data.UserCalendarResponse

/**
 * @author Shubham Pandey
 */
sealed class HomeViewState {

    object ShowLoader: HomeViewState()

    object HideLoader: HomeViewState()

    data class ShowError(val throwable: Throwable): HomeViewState()

    data class ShowFitnessQuotes(val quotes: List<QuoteInfo>): HomeViewState()

    data class ShowUserRoutine(val userRoutine: UserCalendarResponse): HomeViewState()

}