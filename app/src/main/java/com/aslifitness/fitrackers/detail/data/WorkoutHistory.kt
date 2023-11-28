package com.aslifitness.fitrackers.detail.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitrackers.model.CardItem
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */

@Parcelize
@Keep
data class WorkoutHistory(val header: String? = null, val rows: List<CardItem>? = null): Parcelable
