package com.aslifitness.fitracker.detail.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitracker.model.SetCountInfo
import kotlinx.parcelize.Parcelize

/**
* @author Shubham Pandey
*/

@Keep
@Parcelize
data class Workout(
    val image: String? = null,
    val header: String? = null,
    val subHeader: String? = null,
    val qtyInfo: SetCountInfo? = null): Parcelable