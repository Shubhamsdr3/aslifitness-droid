package com.aslifitness.fitracker.profile

import com.aslifitness.fitracker.db.UserDao
import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.ApiService
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/**
 * Created by shubhampandey
 */
class UserRepository(private val apiService: ApiService, private val userDao: UserDao) {

    private suspend fun addUser(user: UserDto) {
        userDao.addUser(user)
    }

    suspend fun updateUserProfile(userDetails: Map<String, String>): Response<ApiResponse<UserDto>> {
        return apiService.updateUserProfile(userDetails)
    }

    suspend fun getUserDetail(uid: String): UserDto? {
        return userDao.getUserDetail(uId = uid)
    }

    suspend fun updateUserName(userId: String, userName: String) {
        userDao.updateUserName(userId = userId, userName = userName)
//        apiService.updateUserProfile()
    }

    suspend fun updateUserAge(userId: String, age: Int) {
        userDao.updateUserAge(userId = userId, age = age)
    }

    suspend fun updateUserWeight(userId: String, weight: Int) {
        userDao.updateUserWeight(userId = userId, weight = weight)
    }

    suspend fun getUserProfile(userId: String) = apiService.getUserProfile(userId)

    suspend fun saveUser(user: UserDto): Response<ApiResponse<UserDto>> {
        addUser(user = user)
        return apiService.saveUser(getRequestParams(user))
    }

    suspend fun updateUserProfileInDb(userDto: UserDto) {
        userDao.updateUser(userDto)
    }

    suspend fun deleteUser() = userDao.deleteUser()

    private fun getRequestParams(userDto: UserDto): Map<String, Any?> {
        val reqParams = mutableMapOf<String, Any?>()
        reqParams["id"] = userDto.id
        reqParams["userId"] = userDto.userId
        reqParams["name"] = userDto.name
        reqParams["phoneNumber"] = userDto.phoneNumber
        reqParams["age"] = userDto.age
        reqParams["profileImage"] = userDto.profileImage
        reqParams["weight"] = userDto.weight
        return reqParams
    }
}