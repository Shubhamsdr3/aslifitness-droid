package com.aslifitness.fitrackers.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */
@Keep
@Parcelize
data class QuoteResponse(
    val isSuccess: Boolean?,
    val data: List<QuoteInfo>? = null
): Parcelable