package com.aslifitness.fitrackers.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */

@Keep
@Parcelize
data class CtaInfo(val text: String? = null, val icon: String? = null, @CtaActionType.Type val action: String? = null): Parcelable
