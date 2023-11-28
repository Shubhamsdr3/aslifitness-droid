package com.aslifitness.fitrackers.model.profile

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitrackers.model.WorkoutDto
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */

@Keep
@Parcelize
data class ProfileDashboard(
    val title: String? = null,
    val items: List<WorkoutDto>? = null,
): Parcelable