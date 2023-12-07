package com.aslifitness.fitrackers.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */
@Keep
@Parcelize
data class SetCountInfo(var setCount: Int = 0): Parcelable