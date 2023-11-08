package com.aslifitness.fitracker.home

import com.aslifitness.fitracker.db.FitnessQuoteDao
import com.aslifitness.fitracker.firebase.FirestoreUtil
import com.aslifitness.fitracker.model.QuoteInfo
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
class HomeRepository @Inject constructor(private val apiService: ApiService, private val fitnessQuoteDao: FitnessQuoteDao) {

    suspend fun fetchWorkoutList(): Flow<NetworkState<ApiResponse<WorkoutResponse>>> {
        return performNetworkCall { apiService.fetchWorkouts() }
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