package com.aslifitness.fitracker.workoutlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.model.WorkoutListResponse
import com.aslifitness.fitracker.network.ApiHandler
import com.aslifitness.fitracker.network.ApiResponse
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
class WorkoutListViewModel @Inject constructor() : ViewModel() {

    private val workoutListMutableLiveData = MutableLiveData<NetworkState<ApiResponse<WorkoutListResponse>>>()
    val workoutListViewState: LiveData<NetworkState<ApiResponse<WorkoutListResponse>>> = workoutListMutableLiveData

    fun fetchWorkoutList() {
        viewModelScope.launch {
            workoutListMutableLiveData.value = NetworkState.Loading
            performNetworkCall { ApiHandler.apiService.getWorkoutList() }
                .catch {
                    workoutListMutableLiveData.value = NetworkState.Error(it)
                }.collect {
                    workoutListMutableLiveData.value = it
                }
        }
    }
}