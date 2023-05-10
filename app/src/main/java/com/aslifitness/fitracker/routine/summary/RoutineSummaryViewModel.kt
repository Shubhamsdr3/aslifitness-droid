package com.aslifitness.fitracker.routine.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.routine.RoutineRepository
import com.aslifitness.fitracker.routine.data.UserRoutineDto
import com.aslifitness.fitracker.sharedprefs.UserStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * Created by shubhampandey
 */
class RoutineSummaryViewModel(private  val repository: RoutineRepository): ViewModel() {

    private val userRoutineSummaryLiveData = MutableLiveData<NetworkState<List<UserRoutineDto>>>()
    private val userRoutineLiveData: LiveData<NetworkState<List<UserRoutineDto>>> = userRoutineSummaryLiveData

    fun fetchUserRoutine() {
        val userId = UserStore.getUserId()
        viewModelScope.launch {
            repository.getUserRoutine()
                .catch {
                    userRoutineSummaryLiveData.value = NetworkState.Error(it)
                }.collect {
                    userRoutineSummaryLiveData.value = NetworkState.Success(it)
                }
        }
    }
}