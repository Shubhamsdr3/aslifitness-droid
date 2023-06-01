package com.aslifitness.fitracker.addworkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.model.addworkout.NewAddWorkout
import com.aslifitness.fitracker.model.addworkout.WorkoutSetInfo
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.sharedprefs.UserStore
import com.aslifitness.fitracker.summary.data.WorkoutSummaryResponse
import com.aslifitness.fitracker.utils.CREATED_AT
import com.aslifitness.fitracker.utils.DURATION
import com.aslifitness.fitracker.utils.SET_DATA
import com.aslifitness.fitracker.utils.USER_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.LinkedList
import javax.inject.Inject

/**
 * @author Shubham Pandey
 */

@HiltViewModel
class AddWorkoutViewModel @Inject constructor(private val repository: WorkoutRepository) : ViewModel() {

    private val addWorkoutMutableState = MutableLiveData<NetworkState<ApiResponse<WorkoutSummaryResponse>>>()
    val addWorkoutState: LiveData<NetworkState<ApiResponse<WorkoutSummaryResponse>>> = addWorkoutMutableState

    private val addWorkoutViewMutableState = MutableLiveData<AddWorkoutState>()
    val addWorkoutViewState: LiveData<AddWorkoutState> = addWorkoutViewMutableState

    private val _addedWorkoutList = MutableLiveData<ArrayList<NewAddWorkout>>()
    val addedWorkoutList: LiveData<ArrayList<NewAddWorkout>> = _addedWorkoutList
    private val doneWorkoutList = mutableMapOf<Int, MutableList<WorkoutSetInfo>>()
    private var totalWeight: Int = 0
    private val userId by lazy { UserStore.getUserId() }

    init {
        _addedWorkoutList.value = ArrayList()
    }

    fun postUserWorkouts(duration: Long) {
        if (addedWorkoutList.value.isNullOrEmpty()) return
        viewModelScope.launch {
            repository.addNewWorkout(getRequestParams(duration))
                .onStart {
                    addWorkoutMutableState.value = NetworkState.Loading
                }.catch {
                    addWorkoutMutableState.value = NetworkState.Error(it)
            }.collect {
                addWorkoutMutableState.value = it
            }
        }
    }

    private fun getRequestParams(duration: Long): Map<String, Any?> {
        addedWorkoutList.value?.forEach {
            if (doneWorkoutList.containsKey(it.workoutId)) {
                it.sets = doneWorkoutList[it.workoutId]
            }
        }
        return mutableMapOf<String, Any?>().apply {
            put(USER_ID, userId)
            put(SET_DATA, addedWorkoutList.value)
            put(CREATED_AT, System.currentTimeMillis())
            put(DURATION, duration)
        }
    }

    fun addNewWorkout(newWorkoutList: List<NewAddWorkout>) {
        _addedWorkoutList.value?.addAll(newWorkoutList)
        this._addedWorkoutList.value = _addedWorkoutList.value
    }

    fun addSetToWorkout(workoutId: Int, info: WorkoutSetInfo) {
        _addedWorkoutList.value?.forEach {
            if (it.workoutId == workoutId) {
                updateWeight(info.weightInKg)
                updateSetCount(workoutId, info)
            }
        }
    }

    private fun updateSetCount(workoutId: Int, info: WorkoutSetInfo) {
        if (info.isDone) {
            if (doneWorkoutList.containsKey(workoutId)) {
                doneWorkoutList[workoutId]?.add(info)
            } else {
                val setList = mutableListOf<WorkoutSetInfo>()
                setList.add(info)
                doneWorkoutList[workoutId] = setList
            }
            var setCount = 0
            for ((_, value) in doneWorkoutList) {
                setCount += value.size
            }
            addWorkoutViewMutableState.value = AddWorkoutState.UpdateSetCount(setCount)
        }
    }

    private fun updateWeight(weightInKg: Int) {
        totalWeight += weightInKg
        addWorkoutViewMutableState.value = AddWorkoutState.UpdateWeight(totalWeight)
    }
}
