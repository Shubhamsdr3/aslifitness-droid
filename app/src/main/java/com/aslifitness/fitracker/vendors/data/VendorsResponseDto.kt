package com.aslifitness.fitracker.vendors.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class VendorsResponseDto(val userId: String? = null, val vendors: List<VendorInfo>? = null): Parcelable