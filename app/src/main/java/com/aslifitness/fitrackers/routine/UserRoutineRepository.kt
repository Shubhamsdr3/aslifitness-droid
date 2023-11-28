package com.aslifitness.fitrackers.routine

import com.aslifitness.fitrackers.network.ApiService

/**
 * Created by shubhampandey
 */
class UserRoutineRepository(private val apiService: ApiService){

    suspend fun fetchUserRoutine(userId: String, pageNumber: Int, pageLimit: Int) = apiService.fetchUserRoutine(userId, pageNumber, pageLimit)
}