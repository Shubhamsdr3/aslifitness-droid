package com.aslifitness.fitracker.stories.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */
@Keep
@Parcelize
data class StoryDto(
    val url: String? = null,
    val thumbnail: String? = null,
    val type: String? = null,
    val title: String? = null,
    val duration: Long? = null,
    val viewCount: String? = null
) : Parcelable