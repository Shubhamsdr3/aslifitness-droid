package com.aslifitness.fitrackers.network

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */
@Keep
data class ApiResponse<T>(var data: T? = null, var isSuccess: Boolean, var statusCode: Int)
