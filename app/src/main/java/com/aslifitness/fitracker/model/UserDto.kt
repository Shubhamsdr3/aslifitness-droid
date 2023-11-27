package com.aslifitness.fitracker.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author Shubham Pandey
 */
@Keep
@Parcelize
@Entity(tableName = "user")
data class UserDto(
    @PrimaryKey
    val id: String,
    @SerializedName("userId") val userId: String? = null,
    val name: String? = null,
    val profileImage: String? = null,
    val weight: String? = null,
    val age: String? = null,
    @SerializedName("phoneNumber") val phoneNumber: String? = null
) : Parcelable
