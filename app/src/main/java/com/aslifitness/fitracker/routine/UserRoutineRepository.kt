package com.aslifitness.fitracker.routine

import com.aslifitness.fitracker.network.ApiService

/**
 * Created by shubhampandey
 */
class UserRoutineRepository(private val apiService: ApiService){

    suspend fun fetchUserRoutine(userId: String, pageNumber: Int, pageLimit: Int) = apiService.fetchUserRoutine(userId, pageNumber, pageLimit)
}