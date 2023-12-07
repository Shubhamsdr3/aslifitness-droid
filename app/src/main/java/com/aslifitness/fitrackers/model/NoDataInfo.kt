package com.aslifitness.fitrackers.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class NoDataInfo(val title: String? = null, val ctaText: String? = null): Parcelable