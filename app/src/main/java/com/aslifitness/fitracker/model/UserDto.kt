package com.aslifitness.fitracker.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */
@Keep
@Parcelize
data class UserDto(
    val userId: String? = null,
    val name: String? = null,
    val profileImage: String? = null,
    val weight: String? = null,
    val age: String? = null,
    val phoneNumber: String? = null
) : Parcelable
