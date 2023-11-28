package com.aslifitness.fitrackers.db

import androidx.room.TypeConverter
import com.aslifitness.fitrackers.routine.data.RoutineInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Created by shubhampandey
 */
class WorkoutConverter {

    @TypeConverter
    fun fromString(value: String?): List<RoutineInfo>? {
        val listType: Type = object : TypeToken<RoutineInfo?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<RoutineInfo>?): String? {
        return Gson().toJson(list)
    }
}