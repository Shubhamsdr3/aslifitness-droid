package com.aslifitness.fitracker.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.addworkout.WorkoutRepository
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.summary.data.WorkoutSummaryResponse
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * Created by shubhampandey
 */
class WorkoutSummaryViewModel(private val repository: WorkoutRepository): ViewModel() {

    private val _workoutSummary = MutableLiveData<NetworkState<ApiResponse<WorkoutSummaryResponse>>>()
    val workoutSummary: LiveData<NetworkState<ApiResponse<WorkoutSummaryResponse>>> = _workoutSummary

    fun getWorkoutSummary(id: String) {
        viewModelScope.launch {
            repository.getWorkoutSummary(id)
                .catch {
                    _workoutSummary.value = NetworkState.Error(it)
                }.collect {
                    _workoutSummary.value = it
                }
        }
    }
}