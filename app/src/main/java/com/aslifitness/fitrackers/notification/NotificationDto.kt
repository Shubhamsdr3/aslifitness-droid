package com.aslifitness.fitrackers.notification

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
data class NotificationDto(
    val title: String?,
    val message: String?,
    val isScheduled: Boolean?,
    val scheduledTime: Long?,
    val deeplinkUrl: String?
): Parcelable