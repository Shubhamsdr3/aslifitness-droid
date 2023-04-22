package com.aslifitness.fitracker.routine

import com.aslifitness.fitracker.db.AppDatabase
import com.aslifitness.fitracker.db.UserRoutine

/**
 * Created by shubhampandey
 */
class RoutineRepository(private val appDatabase: AppDatabase) {

    suspend fun getUserRoutine() = appDatabase.routineDao().fetchUserRoutine()

    suspend fun addNewRoutineToDb(userRoutine: UserRoutine) = appDatabase.routineDao().addRoutine(userRoutine)

    suspend fun deleteRoutine(userRoutine: UserRoutine) = appDatabase.routineDao().deleteRoutine(userRoutine)

//    suspend fun addNewRoutine(userRoutineDto: UserRoutineDto) = apiService.addNewRoutine(userRoutineDto)
}