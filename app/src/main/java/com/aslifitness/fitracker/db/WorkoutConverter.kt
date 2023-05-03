package com.aslifitness.fitracker.db

import androidx.room.TypeConverter
import com.aslifitness.fitracker.routine.data.RoutineWorkout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * Created by shubhampandey
 */
class WorkoutConverter {

    @TypeConverter
    fun fromString(value: String?): List<RoutineWorkout>? {
        val listType: Type = object : TypeToken<List<RoutineWorkout?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<RoutineWorkout>?): String? {
        return Gson().toJson(list)
    }
}