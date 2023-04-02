package com.aslifitness.fitracker.addworkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.firebase.FirestoreUtil
import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.model.WorkoutSetData
import com.aslifitness.fitracker.model.addworkout.NewAddWorkout
import com.aslifitness.fitracker.model.addworkout.NewAddWorkoutResponse
import com.aslifitness.fitracker.model.addworkout.WorkoutSetInfo
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.sharedprefs.UserStore
import com.aslifitness.fitracker.summary.data.WorkoutSummaryResponse
import com.aslifitness.fitracker.utils.CREATED_AT
import com.aslifitness.fitracker.utils.SET_DATA
import com.aslifitness.fitracker.utils.USER_ID
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Shubham Pandey
 */

@HiltViewModel
class AddWorkoutViewModel @Inject constructor(private val repository: WorkoutRepository): ViewModel() {

    private val addWorkoutMutableState = MutableLiveData<NetworkState<ApiResponse<WorkoutSummaryResponse>>>()
    val addWorkoutState: LiveData<NetworkState<ApiResponse<WorkoutSummaryResponse>>> = addWorkoutMutableState

    private val addWorkoutViewMutableState = MutableLiveData<AddWorkoutState>()
    val addWorkoutViewState: LiveData<AddWorkoutState> = addWorkoutViewMutableState

    private val _addedWorkoutList = MutableLiveData<LinkedHashSet<NewAddWorkout>>()
    val addedWorkoutList: LiveData<LinkedHashSet<NewAddWorkout>> = _addedWorkoutList
    private val userId by lazy { UserStore.getUserId() }

    init {
        _addedWorkoutList.value = LinkedHashSet()
        UserStore.putUserId("pande_shubhm123") // Remove this.
    }

    fun postUserWorkouts() {
        if (addedWorkoutList.value.isNullOrEmpty()) return
        addWorkoutMutableState.value = NetworkState.Loading
        viewModelScope.launch {
            repository.addNewWorkout(getRequestParams()).catch {
                addWorkoutMutableState.value = NetworkState.Error(it)
            }.collect {
                addWorkoutMutableState.value = it
            }
        }
    }

    private fun getRequestParams(): Map<String, Any?> {
        val requestParams = mutableMapOf<String, Any?>()
        requestParams[USER_ID] = userId
        requestParams[SET_DATA] = addedWorkoutList.value
        requestParams[CREATED_AT] = System.currentTimeMillis()
        return requestParams
    }

    fun addNewWorkout(newWorkoutList: List<NewAddWorkout>) {
        _addedWorkoutList.value?.addAll(newWorkoutList)
        this._addedWorkoutList.value = _addedWorkoutList.value
    }

    fun addSetToWorkout(info: WorkoutSetInfo) {
        _addedWorkoutList.value?.forEach {
            if (it.workoutId == info.workoutId) {
                it.sets?.add(info)
            }
        }
    }
}