package com.aslifitness.fitrackers.profile

import com.aslifitness.fitrackers.db.UserDao
import com.aslifitness.fitrackers.model.UserDto
import com.aslifitness.fitrackers.network.ApiResponse
import com.aslifitness.fitrackers.network.ApiService
import com.aslifitness.fitrackers.sharedprefs.UserStore
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
        apiService.updateUserProfile(mapOf(Pair("name", userName), Pair("id", UserStore.getUId())))
    }

    suspend fun updateUserAge(userId: String, age: Int) {
        userDao.updateUserAge(userId = userId, age = age)
        apiService.updateUserProfile(mapOf(Pair("age", age), Pair("id", UserStore.getUId())))
    }

    suspend fun updateUserWeight(userId: String, weight: Int) {
        userDao.updateUserWeight(userId = userId, weight = weight)
        apiService.updateUserProfile(mapOf(Pair("weight", weight), Pair("id", UserStore.getUId())))
    }

    suspend fun updateUserProfile(userId: String, imageUrl: String?) {
        userDao.updateUserProfile(userId = userId, imageUrl = imageUrl)
        apiService.updateUserProfile(mapOf(Pair("profileImage", imageUrl), Pair("id", UserStore.getUId())))
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