package com.aslifitness.fitrackers.model.profile

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitrackers.detail.data.WorkoutHistory
import com.aslifitness.fitrackers.model.UserDto
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