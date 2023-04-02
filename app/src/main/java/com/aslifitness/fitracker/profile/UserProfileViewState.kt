package com.aslifitness.fitracker.profile

/**
 * @author Shubham Pandey
 */
sealed class UserProfileViewState {

    object ShowLoader : UserProfileViewState()

    object HideLoader: UserProfileViewState()

    data class ShowError(val throwable: Throwable): UserProfileViewState()
}