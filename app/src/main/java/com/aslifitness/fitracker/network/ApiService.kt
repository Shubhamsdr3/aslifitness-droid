package com.aslifitness.fitracker.network

import com.aslifitness.fitracker.detail.data.WorkoutDetailResponse
import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.model.WorkoutListResponse
import com.aslifitness.fitracker.model.WorkoutResponse
import com.aslifitness.fitracker.model.profile.UserProfileResponse
import com.aslifitness.fitracker.plan.data.UserRoutineResponse
import com.aslifitness.fitracker.summary.data.WorkoutSummaryResponse
import com.aslifitness.fitracker.utils.*
import com.aslifitness.fitracker.vendors.data.VendorsResponseDto
import retrofit2.Response
import retrofit2.http.*

/**
 * @author Shubham Pandey
 */
interface ApiService {

    @GET("/api/home")
    suspend fun fetchWorkouts(): Response<ApiResponse<WorkoutResponse>>

    @GET(GET_WORKOUT_DETAIL)
    suspend fun fetchWorkoutDetail(): Response<ApiResponse<WorkoutDetailResponse>>

    @POST("/api/workout/addWorkout")
    suspend fun addUserWorkout(@Body params: Map<String, @JvmSuppressWildcards Any?>): Response<ApiResponse<WorkoutSummaryResponse>>

    @GET("/api/workout/allWorkouts")
    suspend fun getWorkoutList(): Response<ApiResponse<WorkoutListResponse>>

    @GET("/api/user/profile")
    suspend fun getUserProfile(): Response<ApiResponse<UserProfileResponse>>

    @POST("/api/user/save")
    suspend fun saveUser(@Body userDto: UserDto): Response<ApiResponse<UserDto>>

    @POST("api/fitness-centers")
    suspend fun fetchVendorList(@Body params: Map<String, @JvmSuppressWildcards Any?>): Response<ApiResponse<VendorsResponseDto>>

    @GET("api/{userId}/routine")
    suspend fun fetchUserRoutine(@Path("userId") userId: String, @Query("page_number") pageNumber: Int, @Query("page_limit") pageLimit: Int): Response<ApiResponse<UserRoutineResponse>>

    @GET("api/workout/summary/{id}")
    suspend fun getWorkoutSummary(@Path("id") id: String): Response<ApiResponse<WorkoutSummaryResponse>>
}