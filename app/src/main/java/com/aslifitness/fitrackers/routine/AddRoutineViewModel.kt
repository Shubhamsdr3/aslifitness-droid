package com.aslifitness.fitrackers.routine

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitrackers.network.NetworkState
import com.aslifitness.fitrackers.network.performNetworkCall
import com.aslifitness.fitrackers.routine.data.UserRoutineDto
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
                _userRoutineMutableState.value = NetworkState.Error(ex)
                Log.d("Error", ex.toString())
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