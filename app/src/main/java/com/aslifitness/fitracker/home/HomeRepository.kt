package com.aslifitness.fitracker.home

import com.aslifitness.fitracker.firebase.FirestoreUtil
import com.aslifitness.fitracker.model.WorkoutDto
import com.aslifitness.fitracker.model.WorkoutResponse
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.ApiService
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.network.performNetworkCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Shubham Pandey
 */
class HomeRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun fetchWorkoutList(): Flow<NetworkState<ApiResponse<WorkoutResponse>>> {
        return performNetworkCall { apiService.fetchWorkouts() }
    }

    suspend fun fetchWorkoutListFromFB() = FirestoreUtil.getWorkouts()
}