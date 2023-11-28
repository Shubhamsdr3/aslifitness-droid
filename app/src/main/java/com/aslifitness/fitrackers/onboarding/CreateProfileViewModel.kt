package com.aslifitness.fitrackers.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitrackers.model.UserDto
import com.aslifitness.fitrackers.network.ApiResponse
import com.aslifitness.fitrackers.network.NetworkState
import com.aslifitness.fitrackers.network.performNetworkCall
import com.aslifitness.fitrackers.profile.UserRepository
import com.aslifitness.fitrackers.sharedprefs.UserStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * Created by shubhampandey
 */
class CreateProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val createProfileNetworkMutableState: MutableLiveData<NetworkState<ApiResponse<UserDto>>> = MutableLiveData()
    val createUserProfileNetworkState = createProfileNetworkMutableState
    fun updateUserProfile(updatedDetails: Map<String, String>) {
        viewModelScope.launch {
            createProfileNetworkMutableState.value = NetworkState.Loading
            performNetworkCall {
                userRepository.updateUserProfile(userDetails = updatedDetails)
            }.catch {
                createProfileNetworkMutableState.value = NetworkState.Error(it)
            }.collect { res ->
                createProfileNetworkMutableState.value = res
            }
        }
    }

    fun updateUserInDb(userDto: UserDto) {
        viewModelScope.launch {
            userRepository.updateUserProfileInDb(userDto)
            if (!userDto.userId.isNullOrEmpty()) {
                UserStore.putUserId(userId = userDto.userId)
            }
        }
    }
}