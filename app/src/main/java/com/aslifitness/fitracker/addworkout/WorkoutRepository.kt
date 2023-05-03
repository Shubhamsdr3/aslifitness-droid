package com.aslifitness.fitracker.addworkout

import com.aslifitness.fitracker.network.ApiService
import com.aslifitness.fitracker.network.performNetworkCall
import com.aslifitness.fitracker.routine.data.UserRoutineDto
import javax.inject.Inject

/**
 * Created by shubhampandey
 */
class WorkoutRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun addNewWorkout(requestParams: Map<String, Any?>) = performNetworkCall { apiService.addUserWorkout(requestParams) }

    suspend fun getWorkoutSummary(id: String) = performNetworkCall { apiService.getWorkoutSummary(id) }
}