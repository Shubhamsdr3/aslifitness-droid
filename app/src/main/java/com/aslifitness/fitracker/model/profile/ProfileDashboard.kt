package com.aslifitness.fitracker.model.profile

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitracker.model.CardItem
import com.aslifitness.fitracker.model.WorkoutDto
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