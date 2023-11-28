package com.aslifitness.fitrackers.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aslifitness.fitrackers.model.QuoteInfo
import kotlinx.coroutines.flow.Flow

/**
 * Created by shubhampandey
 */

@Dao
interface FitnessQuoteDao {

    @Query("SELECT * FROM fitness_quote ORDER BY created_at DESC")
    fun getFitnessQuotes(): Flow<List<QuoteInfo>>

    @Insert
    suspend fun addQuote(quoteInfo: QuoteInfo)

    @Delete
    suspend fun deleteQuote(quoteInfo: QuoteInfo)

    @Query("UPDATE fitness_quote SET isLiked =:isLiked WHERE id=:id")
    suspend fun updateLike(isLiked: Boolean, id: Int)
}