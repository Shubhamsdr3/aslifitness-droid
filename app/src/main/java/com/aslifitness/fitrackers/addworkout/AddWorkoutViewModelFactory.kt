package com.aslifitness.fitrackers.addworkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitrackers.network.ApiHandler
import java.lang.IllegalArgumentException

/**
 * @author Shubham Pandey
 */
class AddWorkoutViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddWorkoutViewModel::class.java)) {
            return AddWorkoutViewModel(WorkoutRepository(ApiHandler.apiService)) as T
        }
        throw IllegalArgumentException("Wrong type viewmodel")
    }
}