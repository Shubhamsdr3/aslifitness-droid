package com.aslifitness.fitracker.network

import com.aslifitness.fitracker.detail.data.WorkoutDetailResponse
import com.aslifitness.fitracker.model.AddWorkoutDto
import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.model.WorkoutListResponse
import com.aslifitness.fitracker.model.WorkoutResponse
import com.aslifitness.fitracker.model.profile.UserProfileResponse
import com.aslifitness.fitracker.utils.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @author Shubham Pandey
 */
interface ApiService {

    @GET("/api/home")
    suspend fun fetchWorkouts(): Response<ApiResponse<WorkoutResponse>>

    @GET(GET_WORKOUT_DETAIL)
    suspend fun fetchWorkoutDetail(): Response<ApiResponse<WorkoutDetailResponse>>

    @GET("/api/addWorkouts")
    suspend fun fetchAddWorkoutDetail(): Response<ApiResponse<AddWorkoutDto>>

    @GET("/api/allWorkouts")
    suspend fun getWorkoutList(): Response<ApiResponse<WorkoutListResponse>>

    @GET("/api/user/profile")
    suspend fun getUserProfile(): Response<ApiResponse<UserProfileResponse>>

    @POST("/api/user/save")
    suspend fun saveUser(@Body userDto: UserDto): Response<ApiResponse<UserDto>>
}