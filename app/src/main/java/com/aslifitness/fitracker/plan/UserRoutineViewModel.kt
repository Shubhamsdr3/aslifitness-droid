package com.aslifitness.fitracker.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.network.performNetworkCall
import com.aslifitness.fitracker.plan.data.UserRoutineResponse
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * Created by shubhampandey
 */
class UserRoutineViewModel(private val repository: UserRoutineRepository): ViewModel() {

    private var pageNumber = 1
    private var pageLimit = 3

    private val _userRoutineViewState: MutableLiveData<NetworkState<ApiResponse<UserRoutineResponse>>> = MutableLiveData()
    val userRoutineViewState: LiveData<NetworkState<ApiResponse<UserRoutineResponse>>> = _userRoutineViewState

    fun fetchUserRoutine(userId: String) {
        viewModelScope.launch {
            performNetworkCall { repository.fetchUserRoutine(userId, pageNumber, pageLimit) }
                .catch {
                    _userRoutineViewState.value = NetworkState.Error(it)
                }.collect { response ->
                    _userRoutineViewState.value = response
                }
        }
    }
}