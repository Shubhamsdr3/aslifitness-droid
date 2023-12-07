package com.aslifitness.fitrackers.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class MenuOption(
    val title: String? = null,
    val image: String? = null,
    val cta: CtaInfo? = null
): Parcelable
