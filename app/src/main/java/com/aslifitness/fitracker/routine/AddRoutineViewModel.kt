package com.aslifitness.fitracker.routine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.addworkout.WorkoutRepository
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.network.performNetworkCall
import com.aslifitness.fitracker.routine.data.UserRoutineDto
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Created by shubhampandey
 */
class AddRoutineViewModel(private val repository: WorkoutRepository) : ViewModel() {

    private val _userRoutineMutableState = MutableLiveData<NetworkState<ApiResponse<UserRoutineDto>>>()
    val userRoutineState: LiveData<NetworkState<ApiResponse<UserRoutineDto>>> = _userRoutineMutableState

    fun saveUserRoutine(userRoutineDto: UserRoutineDto) {
        viewModelScope.launch {
            performNetworkCall { repository.addNewRoutine(userRoutineDto) }
                .onStart {
                    _userRoutineMutableState.value = NetworkState.Loading
                }.catch {
                    _userRoutineMutableState.value = NetworkState.Error(it)
                }.collect {
                    _userRoutineMutableState.value = it
                }
        }
    }
}