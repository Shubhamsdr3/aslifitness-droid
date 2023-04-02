package com.aslifitness.fitracker.stories.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */
@Keep
@Parcelize
data class UserStoryInfo (
    val header: String? = null,
    val userId: String? = null,
    val userName : String? = null,
    val profileImage: String? = null,
    val storiesList : List<StoryDto>? = null
) : Parcelable