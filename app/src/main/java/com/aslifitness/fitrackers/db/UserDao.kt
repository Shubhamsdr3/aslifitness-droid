package com.aslifitness.fitrackers.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aslifitness.fitrackers.model.UserDto

/**
 * Created by shubhampandey
 */

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE id=:uId")
    fun getUserDetail(uId: String): UserDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userDto: UserDto)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(userDto: UserDto)

    @Query("UPDATE user SET name=:userName WHERE userId=:userId")
    suspend fun updateUserName(userId: String, userName: String)

    @Query("UPDATE user SET age=:age WHERE userId=:userId")
    suspend fun updateUserAge(userId: String, age: Int)

    @Query("UPDATE user SET weight=:weight WHERE userId=:userId")
    suspend fun updateUserWeight(userId: String, weight: Int)

    @Query("DELETE FROM user")
    suspend fun deleteUser()
}