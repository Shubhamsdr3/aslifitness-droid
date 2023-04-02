package com.aslifitness.fitracker.model.addworkout

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitracker.model.CtaInfo
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class NewAddWorkout(
    val image: String? = null,
    val header: String? = null,
    val subHeader: String? = null,
    val sets: List<WorkoutSetInfo>? = null,
    val ctas: List<CtaInfo>? = null
): Parcelable
