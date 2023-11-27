package com.aslifitness.fitracker.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.model.profile.UserProfileResponse
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.network.performNetworkCall
import com.aslifitness.fitracker.sharedprefs.UserStore
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author Shubham Pandey
 */

class UserProfileViewModel (private val userRepository: UserRepository): ViewModel() {

    private val userProfileNetworkMutableState: MutableLiveData<NetworkState<ApiResponse<UserProfileResponse>>> = MutableLiveData()
    val userProfileNetworkState: LiveData<NetworkState<ApiResponse<UserProfileResponse>>> = userProfileNetworkMutableState

    private val userProfileViewMutableState: MutableLiveData<UserProfileViewState> = MutableLiveData()
    val userProfileViewState: LiveData<UserProfileViewState> = userProfileViewMutableState

    fun fetchUserProfile(uid: String) {
        viewModelScope.launch {
            userProfileNetworkMutableState.value = NetworkState.Loading
            performNetworkCall { userRepository.getUserProfile(uid) }
                .catch {
                    userProfileNetworkMutableState.value = NetworkState.Error(it)
                }.collect { response ->
                    userProfileNetworkMutableState.value = response
                }
        }
    }

    fun showUserDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = userRepository.getUserDetail(uid = UserStore.getUId())
                userProfileViewMutableState.postValue(UserProfileViewState.ShowUserDetail(user))
            } catch (ex: Exception) {
                Timber.d(ex)
            }
        }
    }

    fun signOut(apiClient: GoogleApiClient) {
        UserStore.signOut(apiClient) {
            viewModelScope.launch(Dispatchers.IO) {
                UserStore.putUId("")
                userRepository.deleteUser()
                userProfileViewMutableState.postValue(UserProfileViewState.OnUserLogout)
            }
        }
    }

    fun updateUserName(userName: String) {
        viewModelScope.launch {
            userRepository.updateUserName(UserStore.getUserId(), userName = userName)
        }
    }

    fun updateUserAge(age: Int) {
        viewModelScope.launch {
            userRepository.updateUserAge(UserStore.getUId(), age)
        }
    }

    fun updateUserWeight(weight: Int) {
        viewModelScope.launch {
            userRepository.updateUserWeight(UserStore.getUId(), weight)
        }
    }
}