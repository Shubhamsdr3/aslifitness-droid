package com.aslifitness.fitrackers.network

import com.aslifitness.fitrackers.detail.data.WorkoutDetailResponse
import com.aslifitness.fitrackers.model.QuoteInfo
import com.aslifitness.fitrackers.model.UserDto
import com.aslifitness.fitrackers.model.WorkoutListResponse
import com.aslifitness.fitrackers.model.WorkoutResponse
import com.aslifitness.fitrackers.model.profile.UserProfileResponse
import com.aslifitness.fitrackers.routine.data.UserCalendarResponse
import com.aslifitness.fitrackers.routine.data.UserRoutineDto
import com.aslifitness.fitrackers.summary.data.WorkoutSummaryResponse
import com.aslifitness.fitrackers.utils.*
import com.aslifitness.fitrackers.vendors.data.VendorsResponseDto
import retrofit2.Response
import retrofit2.http.*

/**
 * @author Shubham Pandey
 */
interface ApiService {

    @GET("/api/home")
    suspend fun fetchWorkouts(@Query("uid") uid: String?): Response<ApiResponse<WorkoutResponse>>

    @GET(GET_WORKOUT_DETAIL)
    suspend fun fetchWorkoutDetail(): Response<ApiResponse<WorkoutDetailResponse>>

    @POST("/api/workout/addWorkout")
    suspend fun addUserWorkout(@Body params: Map<String, @JvmSuppressWildcards Any?>): Response<ApiResponse<WorkoutSummaryResponse>>

    @GET("/api/workout/allWorkouts")
    suspend fun getWorkoutList(): Response<ApiResponse<WorkoutListResponse>>

    @GET("/api/user/{userId}/profile")
    suspend fun getUserProfile(@Path("userId") userId: String): Response<ApiResponse<UserProfileResponse>>

    @POST("/api/user/save")
    suspend fun saveUser(@Body params: Map<String, @JvmSuppressWildcards Any?>): Response<ApiResponse<UserDto>>

    @POST("/api/user/update")
    suspend fun updateUserProfile(@Body params: Map<String, @JvmSuppressWildcards Any?>): Response<ApiResponse<UserDto>>

    @POST("api/fitness-centers")
    suspend fun fetchVendorList(@Body params: Map<String, @JvmSuppressWildcards Any?>): Response<ApiResponse<VendorsResponseDto>>

    @GET("api/{userId}/routine")
    suspend fun fetchUserRoutineType(@Path("userId") userId: String?, @Query("routineType") routineType: String?): Response<ApiResponse<UserCalendarResponse>>

    @GET("api/{userId}/routine")
    suspend fun fetchUserRoutine(@Path("userId") userId: String, @Query("page_number") pageNumber: Int, @Query("page_limit") pageLimit: Int): Response<ApiResponse<UserCalendarResponse>>

    @GET("api/workout/summary/{id}")
    suspend fun getWorkoutSummary(@Path("id") id: String): Response<ApiResponse<WorkoutSummaryResponse>>

    @POST("api/workout/addRoutine")
    suspend fun addNewRoutine(@Body params: UserRoutineDto): Response<ApiResponse<UserRoutineDto>>

    @GET("api/fitness-quotes")
    suspend fun getFitnessQuote(): Response<ApiResponse<List<QuoteInfo>>>

}