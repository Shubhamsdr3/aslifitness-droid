package com.aslifitness.fitracker.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.firebase.FirestoreUtil
import com.aslifitness.fitracker.model.WorkoutDto
import com.aslifitness.fitracker.model.WorkoutResponse
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.google.protobuf.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Shubham Pandey
 */

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel() {

    private val _homeViewState: MutableLiveData<NetworkState<ApiResponse<WorkoutResponse>>> = MutableLiveData()
    val homeViewState: LiveData<NetworkState<ApiResponse<WorkoutResponse>>> = _homeViewState

    fun getWorkoutList() {
        viewModelScope.launch {
            _homeViewState.value = NetworkState.Loading
            repository.fetchWorkoutList()
                .catch { error ->
                    _homeViewState.value = NetworkState.Error(error)
                }.collect { response ->
                    _homeViewState.value = response
                }
        }
    }

    fun getWorkoutListFromFB() {
        viewModelScope.launch {
            _homeViewState.value = NetworkState.Loading
            repository.fetchWorkoutListFromFB()
                .catch { error ->
                    _homeViewState.value =  NetworkState.Error(error)
                }.collect { response ->
//                    _homeViewState.value = response
                }
        }
    }
}