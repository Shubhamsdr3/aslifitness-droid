package com.aslifitness.fitrackers.vendors.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class VendorInfo(
    val businessStatus: String? = null,
    val icon: String? = null,
    val name: String? = null,
    val openingHours: OpeningHours? = null,
    val photos: List<PhotoInfo>? = null,
    val placeId: String? = null,
    val rating: Float? = null,
    val userRatingTotal: String? = null
): Parcelable

@Parcelize
data class OpeningHours(
    val openNow: Boolean = false
): Parcelable

@Parcelize
data class PhotoInfo(
    val height: Int,
    val photoReference: String?,
    val width: Int
): Parcelable