package com.aslifitness.fitrackers.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.aslifitness.fitrackers.detail.data.Workout
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */
@Keep
@Parcelize
data class CardItem(
    val leftIcon: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val rightIcon: String? = null,
    val data: List<Workout>? = null,
    @CtaActionType.Type val action: String? = null,
    val actionUrl: String? = null,
): Parcelable