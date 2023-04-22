package com.aslifitness.fitracker.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 * Created by shubhampandey
 */
@Keep
@Parcelize
data class Reminder(var day: String?, var time: Date? = null, var isRepeat: Boolean = true): Parcelable