package com.aslifitness.fitrackers.routine

import com.aslifitness.fitrackers.db.AppDatabase
import com.aslifitness.fitrackers.network.ApiService
import com.aslifitness.fitrackers.network.performNetworkCall
import com.aslifitness.fitrackers.routine.data.UserRoutineDto

/**
 * Created by shubhampandey
 */
class RoutineRepository constructor(private val apiService: ApiService, private val appDatabase: AppDatabase) {

    fun getUserRoutine() = appDatabase.routineDao().fetchUserRoutine()

    suspend fun addNewRoutineToDb(userRoutine: UserRoutineDto) = appDatabase.routineDao().addRoutine(userRoutine)

    suspend fun deleteRoutine(userRoutine: UserRoutineDto) = appDatabase.routineDao().deleteRoutine(userRoutine)

    suspend fun addNewRoutine(requestParams: UserRoutineDto) =  apiService.addNewRoutine(requestParams)

    suspend fun fetchUserRoutine(userId: String, pageNumber: Int, offset: Int) = performNetworkCall { apiService.fetchUserRoutine(userId, pageNumber, offset) }
}