package com.aslifitness.fitracker.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.model.profile.UserProfileResponse
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.ApiService
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.network.performNetworkCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Shubham Pandey
 */

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val apiService: ApiService): ViewModel() {

    private val userProfileMutableState: MutableLiveData<NetworkState<ApiResponse<UserProfileResponse>>> = MutableLiveData()
    val userProfileViewState: LiveData<NetworkState<ApiResponse<UserProfileResponse>>> = userProfileMutableState

    fun fetchUserProfile(userId: String) {
        viewModelScope.launch {
            userProfileMutableState.value = NetworkState.Loading
            performNetworkCall { apiService.getUserProfile(userId) }
                .catch {
                    userProfileMutableState.value = NetworkState.Error(it)
                }.collect { response ->
                    userProfileMutableState.value = response
                }
        }
    }
}