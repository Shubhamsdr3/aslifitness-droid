package com.aslifitness.fitracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Parcelize
data class MenuOption(
    val title: String? = null,
    val image: String? = null,
    val cta: CtaInfo? = null
): Parcelable
