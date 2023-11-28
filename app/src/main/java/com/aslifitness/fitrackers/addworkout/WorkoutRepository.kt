package com.aslifitness.fitrackers.addworkout

import com.aslifitness.fitrackers.network.ApiService
import com.aslifitness.fitrackers.network.performNetworkCall
import javax.inject.Inject

/**
 * Created by shubhampandey
 */
class WorkoutRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun addNewWorkout(requestParams: Map<String, Any?>) = performNetworkCall { apiService.addUserWorkout(requestParams) }

    suspend fun getWorkoutSummary(id: String) = performNetworkCall { apiService.getWorkoutSummary(id) }
}