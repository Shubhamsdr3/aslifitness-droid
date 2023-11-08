package com.aslifitness.fitracker.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */
@Parcelize
@Entity(tableName = "fitness_quote")
data class QuoteInfo(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val quote: String? = null,
    val author: String? = null,
    val category: String?,
    @ColumnInfo("created_at")
    var createdAt: Long? = null,
    var isLiked: Boolean = false
): Parcelable