package com.aslifitness.fitracker.addworkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.firebase.FirestoreUtil
import com.aslifitness.fitracker.model.AddWorkoutDto
import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.model.WorkoutSetData
import com.aslifitness.fitracker.network.ApiHandler
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.network.performNetworkCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Shubham Pandey
 */

@HiltViewModel
class AddWorkoutViewModel @Inject constructor(): ViewModel() {

    private val addWorkoutMutableState = MutableLiveData<NetworkState<ApiResponse<AddWorkoutDto>>>()
    val addWorkoutState: LiveData<NetworkState<ApiResponse<AddWorkoutDto>>> = addWorkoutMutableState

    private val addWorkoutViewMutableState = MutableLiveData<AddWorkoutState>()
    val addWorkoutViewState: LiveData<AddWorkoutState> = addWorkoutViewMutableState

    private val workoutSetListData = MutableLiveData<NetworkState<List<WorkoutSetData>>>()
    val workoutSetListLiveData: LiveData<NetworkState<List<WorkoutSetData>>> = workoutSetListData

    private val saveWorkoutMutableLiveData = MutableLiveData<NetworkState<WorkoutSetData>>()
    val saveWorkoutLiveData: LiveData<NetworkState<WorkoutSetData>> = saveWorkoutMutableLiveData

    fun fetchAddWorkoutDetail() {
        addWorkoutMutableState.value = NetworkState.Loading
        viewModelScope.launch {
            performNetworkCall {
                ApiHandler.apiService.fetchAddWorkoutDetail()
            }.catch {
                addWorkoutMutableState.value = NetworkState.Error(it)
            }.collect {
                addWorkoutMutableState.value = it
            }
        }
    }

    fun fetchWorkoutSets() {
        viewModelScope.launch {
            workoutSetListData.value = NetworkState.Loading
            FirestoreUtil.getWorkoutSet()
                .catch {
                    workoutSetListData.value = NetworkState.Error(it)
                }.collect {
                    workoutSetListData.value = it
                }
        }
    }

    fun saveWorkout(workoutSet: WorkoutSetData) {
        viewModelScope.launch {
            saveWorkoutMutableLiveData.value = NetworkState.Loading
            delay(1000)
            FirestoreUtil.saveWorkoutHistory(workoutSet)
                .catch {
                    saveWorkoutMutableLiveData.value = NetworkState.Error(it)
                }.collect {
                    saveWorkoutMutableLiveData.value = it
                }
        }
    }

    fun updateViewState(viewState: AddWorkoutState) {
        addWorkoutViewMutableState.value = viewState
    }

    fun saveInitial(workoutSetData: WorkoutSetData) {
        viewModelScope.launch {
            FirestoreUtil.saveInitialSet(workoutSetData)
                .catch {

                }.collect {

                }
        }
    }

    fun saveUser(userDto: UserDto) {
        viewModelScope.launch {
            FirestoreUtil.saveUser(userDto)
                .catch {

                }.collect {

                }
        }
    }
}