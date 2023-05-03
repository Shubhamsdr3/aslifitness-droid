package com.aslifitness.fitracker.routine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.network.performNetworkCall
import com.aslifitness.fitracker.routine.data.UserRoutineDto
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

/**
 * Created by shubhampandey
 */
class AddRoutineViewModel(private val repository: RoutineRepository) : ViewModel() {

    private val _userRoutineMutableState = MutableLiveData<NetworkState<UserRoutineDto>>()
    val userRoutineState: LiveData<NetworkState<UserRoutineDto>> = _userRoutineMutableState

    fun saveRoutineToLocalDb(userRoutineDto: UserRoutineDto) {
        _userRoutineMutableState.value = NetworkState.Loading
        viewModelScope.launch {
            try {
                repository.addNewRoutineToDb(userRoutineDto)
                _userRoutineMutableState.value = NetworkState.Success(userRoutineDto)
            } catch (ex: IOException) {
                Timber.d(ex.stackTraceToString())
                _userRoutineMutableState.value = NetworkState.Error(ex)
            }
        }
    }

    fun saveUserRoutine(userRoutineDto: UserRoutineDto) {
        viewModelScope.launch {
            performNetworkCall { repository.addNewRoutine(userRoutineDto) }
                .onStart {
                    _userRoutineMutableState.value = NetworkState.Loading
                }.catch {
                    _userRoutineMutableState.value = NetworkState.Error(it)
                }.collect {
//                    _userRoutineMutableState.value = it
                }
        }
    }

    fun scheduleAlarm(data: UserRoutineDto?) {
        data?.run {

        }
    }
}