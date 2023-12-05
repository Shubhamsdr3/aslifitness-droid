package com.aslifitness.fitrackers.sharedprefs

import android.content.Context
import com.aslifitness.fitrackers.FitApp
import com.aslifitness.fitrackers.model.UserDto
import com.aslifitness.fitrackers.utils.EMPTY
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * @author Shubham Pandey
 */
object UserStore {

    private const val USER_STORE = "user_store"
    private const val USER_ID = "user_id"
    private const val USER_DETAIL = "user_detail"
    private const val FCM_TOKEN = "fcm_token"
    private const val IS_NEW_USER = "is_new_user"
    private const val IS_USER_AUTHENTICATED = "is_user_authenticated"
    private const val UID = "uid"

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val sharedPreferences = FitApp.getAppContext()?.getSharedPreferences(USER_STORE, Context.MODE_PRIVATE)

    fun isUserAuthenticated() = sharedPreferences?.getBoolean(IS_USER_AUTHENTICATED, false) ?: false

    fun setUserAuthenticated(isAuthenticated: Boolean) {
        sharedPreferences?.run {
            edit().putBoolean(IS_USER_AUTHENTICATED, isAuthenticated)
        }?.commit()
    }

    fun putUserId(userId: String) {
        sharedPreferences?.run {
            edit().putString(USER_ID, userId)
        }?.commit()
    }

    fun getUserId(): String {
        return sharedPreferences?.getString(USER_ID, EMPTY) ?: EMPTY
    }

    fun getUId() = sharedPreferences?.getString(UID, EMPTY) ?: EMPTY

    fun putUId(uId: String) {
        sharedPreferences?.run {
            edit().putString(UID, uId)
        }?.commit()
    }

    fun getUserDetail(): UserDto? {
        val userJson = sharedPreferences?.getString(USER_DETAIL, EMPTY)
        val type = object : TypeToken<UserDto>() {}.type
        return Gson().fromJson(userJson, type)
    }

    fun putUserDetail(userDto: UserDto) {
        val type = object : TypeToken<UserDto>() {}.type
        sharedPreferences?.run {
            edit().putString(USER_DETAIL, Gson().toJson(userDto, type))
        }?.commit()
    }

    fun saveFCMToken(token: String) {
        sharedPreferences?.run {
            edit().putString(FCM_TOKEN, token)
        }?.commit()
    }

    fun getFCMToken(): String {
        return sharedPreferences?.getString(FCM_TOKEN, EMPTY) ?: EMPTY
    }

    fun isNewUser() = FirebaseAuth.getInstance().currentUser == null

    fun signOut(apiClient: GoogleApiClient, onSignOut: () -> Unit) {
        firebaseAuth.signOut()
        firebaseAuth.addAuthStateListener {
            if (firebaseAuth.currentUser == null) {
                onSignOut.invoke()
            }
        }
        Auth.GoogleSignInApi.signOut(apiClient)
    }
}