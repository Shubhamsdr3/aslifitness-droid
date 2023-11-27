package com.aslifitness.fitracker.profile

import com.aslifitness.fitracker.model.UserDto

/**
 * @author Shubham Pandey
 */
sealed class UserProfileViewState {

    data class ShowUserDetail(val userDto: UserDto?): UserProfileViewState()

    object OnUserLogout: UserProfileViewState()
}