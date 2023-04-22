package com.aslifitness.fitracker.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by shubhampandey
 */

@Dao
interface RoutineDao {

    @Query("SELECT * FROM user_routine")
    suspend fun fetchUserRoutine(): List<UserRoutine>

    @Insert
    suspend fun addRoutine(userRoutine: UserRoutine)

    @Delete
    suspend fun deleteRoutine(userRoutine: UserRoutine)
}