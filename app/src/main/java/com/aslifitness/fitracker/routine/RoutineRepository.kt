package com.aslifitness.fitracker.routine

import com.aslifitness.fitracker.db.AppDatabase
import com.aslifitness.fitracker.network.ApiService
import com.aslifitness.fitracker.routine.data.UserRoutineDto

/**
 * Created by shubhampandey
 */
class RoutineRepository constructor(private val apiService: ApiService, private val appDatabase: AppDatabase) {

    suspend fun getUserRoutine() = appDatabase.routineDao().fetchUserRoutine()

    suspend fun addNewRoutineToDb(userRoutine: UserRoutineDto) = appDatabase.routineDao().addRoutine(userRoutine)

    suspend fun deleteRoutine(userRoutine: UserRoutineDto) = appDatabase.routineDao().deleteRoutine(userRoutine)

    suspend fun addNewRoutine(requestParams: UserRoutineDto) =  apiService.addNewRoutine(requestParams)

//    suspend fun fetchUserRoutine(userId: String) = performNetworkCall { apiService.fetchUserRoutine(userId) }
}