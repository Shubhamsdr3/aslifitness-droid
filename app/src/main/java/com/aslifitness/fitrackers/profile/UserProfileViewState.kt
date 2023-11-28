package com.aslifitness.fitrackers.profile

import com.aslifitness.fitrackers.model.UserDto

/**
 * @author Shubham Pandey
 */
sealed class UserProfileViewState {

    data class ShowUserDetail(val userDto: UserDto?): UserProfileViewState()

    object OnUserLogout: UserProfileViewState()
}