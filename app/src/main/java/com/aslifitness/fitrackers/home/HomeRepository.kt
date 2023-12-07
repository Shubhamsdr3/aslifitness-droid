package com.aslifitness.fitrackers.home

import com.aslifitness.fitrackers.db.FitnessQuoteDao
import com.aslifitness.fitrackers.firebase.FirestoreUtil
import com.aslifitness.fitrackers.model.QuoteInfo
import com.aslifitness.fitrackers.model.WorkoutResponse
import com.aslifitness.fitrackers.network.ApiResponse
import com.aslifitness.fitrackers.network.ApiService
import com.aslifitness.fitrackers.network.NetworkState
import com.aslifitness.fitrackers.network.performNetworkCall
import com.aslifitness.fitrackers.routine.data.UserCalendarResponse
import com.aslifitness.fitrackers.routine.data.UserRoutineDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Shubham Pandey
 */
class HomeRepository @Inject constructor(private val apiService: ApiService, private val fitnessQuoteDao: FitnessQuoteDao) {

    suspend fun fetchWorkoutList(userId: String?): Flow<NetworkState<ApiResponse<WorkoutResponse>>> {
        return performNetworkCall { apiService.fetchWorkouts(uid = userId) }
    }

    suspend fun fetchRoutineType(userId: String?, type: String): Flow<NetworkState<ApiResponse<UserCalendarResponse>>> {
        return performNetworkCall { apiService.fetchUserRoutineType(userId, type) }
    }

    suspend fun fetchFitnessQuotes(): Flow<NetworkState<ApiResponse<List<QuoteInfo>>>> {
        return performNetworkCall { apiService.getFitnessQuote() }
    }

    suspend fun fetchWorkoutListFromFB() = FirestoreUtil.getWorkouts()

    suspend fun saveQuote(quoteInfo: QuoteInfo) {
        fitnessQuoteDao.addQuote(quoteInfo)
    }

    suspend fun updateLike(isLiked: Boolean, quoteId: Int) {
        fitnessQuoteDao.updateLike(isLiked, quoteId)
    }

    suspend fun getQuotesFromDB() = fitnessQuoteDao.getFitnessQuotes()
}