package com.aslifitness.fitracker.sharedprefs

import android.content.Context
import com.aslifitness.fitracker.FitApp
import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.utils.EMPTY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author Shubham Pandey
 */
object UserStore {

    private const val USER_STORE = "user_store"
    private const val USER_ID = "user_id"
    private const val USER_DETAIL = "user_detail"

    private val sharedPreferences = FitApp.getAppContext()?.getSharedPreferences(USER_STORE, Context.MODE_PRIVATE)

    fun putUserId(userId: String) {
        sharedPreferences?.run {
            edit().putString(USER_ID, userId)
        }?.commit()
    }

    fun getUserId(): String {
        return sharedPreferences?.getString(USER_ID, EMPTY) ?: EMPTY
    }

    fun getUserDetail(): UserDto? {
        val userJson = sharedPreferences?.getString(USER_DETAIL, EMPTY)
        val type = object : TypeToken<UserDto>() {}.type
        return Gson().fromJson(userJson, type)
    }

    fun putUserDetail(userDto: UserDto) {
        val type = object : TypeToken<UserDto>() {}.type
        sharedPreferences?.run {
            edit().putString(USER_ID, Gson().toJson(userDto, type))
        }?.commit()
    }
}