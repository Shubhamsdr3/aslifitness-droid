package com.aslifitness.fitracker.model.profile

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitracker.detail.data.WorkoutHistory
import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.model.WorkoutResponse
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */

@Keep
@Parcelize
data class UserProfileResponse(
    val profile: UserDto? = null,
    val dashboard: ProfileDashboard? = null,
    val workouts: WorkoutHistory? = null
): Parcelable