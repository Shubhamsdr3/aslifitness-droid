package com.aslifitness.fitracker.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by shubhampandey
 */

@Entity(tableName = "user_routine")
data class UserRoutine(

    @PrimaryKey
    var workoutId: Int = 0,

    @ColumnInfo("routine_title")
    var routineTitle: String? = null,

    @ColumnInfo("workout_name")
    var workoutName: String? = null,

    @ColumnInfo("isRepeat")
    var isRepeat: Boolean? = null,

    @ColumnInfo("minutes")
    var minutes: Int? = null,

    @ColumnInfo("hourOfDay")
    var hourOfDay: Int? = null,

    @ColumnInfo("dayOfMonth")
    var dayOfMonth: Int? = null,

    @ColumnInfo("monthOfYr")
    var monthOfYr: Int? = null,

    @ColumnInfo("year")
    var year: Int? = null,

    @ColumnInfo("created_at")
    var createdAt: Long? = null
)