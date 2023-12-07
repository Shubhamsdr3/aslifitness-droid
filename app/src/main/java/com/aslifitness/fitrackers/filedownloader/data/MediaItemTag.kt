package com.aslifitness.fitrackers.filedownloader.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MediaItemTag(val duration: Long, val title: String): Parcelable