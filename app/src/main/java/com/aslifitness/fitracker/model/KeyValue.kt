package com.aslifitness.fitracker.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class KeyValue(
    val key: String? = null,
    val value: @RawValue Any? = null
): Parcelable