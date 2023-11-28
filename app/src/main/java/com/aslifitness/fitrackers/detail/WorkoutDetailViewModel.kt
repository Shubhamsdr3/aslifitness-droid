package com.aslifitness.fitrackers.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitrackers.detail.data.WorkoutDetailResponse
import com.aslifitness.fitrackers.network.ApiHandler
import com.aslifitness.fitrackers.network.ApiResponse
import com.aslifitness.fitrackers.network.NetworkState
import com.aslifitness.fitrackers.network.performNetworkCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Shubham Pandey
 */

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(): ViewModel() {

    private val _workoutViewState: MutableLiveData<NetworkState<ApiResponse<WorkoutDetailResponse>>> = MutableLiveData()
    val workoutViewState: LiveData<NetworkState<ApiResponse<WorkoutDetailResponse>>> = _workoutViewState

    fun getLastWorkout() {
        viewModelScope.launch {
            _workoutViewState.value = NetworkState.Loading
            performNetworkCall { ApiHandler.apiService.fetchWorkoutDetail() }
                .catch { error ->
                    _workoutViewState.value =  NetworkState.Error(error)
                }.collect { response ->
                    _workoutViewState.value = response
                }
        }
    }
}