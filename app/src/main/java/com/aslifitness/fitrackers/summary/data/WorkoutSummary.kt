package com.aslifitness.fitrackers.summary.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitrackers.model.KeyValue
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class WorkoutSummary(
    val image: String? = null,
    val header: String? = null,
    val title: String? = null,
    val subTitle: String? = null,
    val durationInfo: KeyValue? = null,
    val volumeInfo: KeyValue? = null,
    val setInfo: KeyValue? = null,
): Parcelable