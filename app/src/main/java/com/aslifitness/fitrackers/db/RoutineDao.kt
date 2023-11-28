package com.aslifitness.fitrackers.db

import androidx.room.*
import com.aslifitness.fitrackers.routine.data.UserRoutineDto
import kotlinx.coroutines.flow.Flow

/**
 * Created by shubhampandey
 */

@Dao
interface RoutineDao {

    @Query("SELECT * FROM user_routine ORDER BY created_at DESC")
    fun fetchUserRoutine(): Flow<List<UserRoutineDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRoutine(userRoutine: UserRoutineDto)

    @Delete
    suspend fun deleteRoutine(userRoutine: UserRoutineDto)
}