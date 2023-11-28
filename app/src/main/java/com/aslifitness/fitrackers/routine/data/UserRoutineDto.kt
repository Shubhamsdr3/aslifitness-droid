package com.aslifitness.fitrackers.routine.data

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Created by shubhampandey
 */

@Keep
@Parcelize
@Entity(tableName = "user_routine")
data class UserRoutineDto(

    @PrimaryKey(autoGenerate = true)
    var routineId: Int = 0,

    @ColumnInfo("userId")
    val userId: String? = null,

    @ColumnInfo("title")
    val title: String? = null,

    @ColumnInfo("workouts")
    val workouts: List<RoutineWorkout>? = null,

    @ColumnInfo("created_at")
    val createdAt: Long?

): Parcelable