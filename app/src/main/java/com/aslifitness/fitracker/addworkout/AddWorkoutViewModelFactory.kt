package com.aslifitness.fitracker.addworkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/**
 * @author Shubham Pandey
 */
class AddWorkoutViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddWorkoutViewModel::class.java)) {
            return AddWorkoutViewModel() as T
        }
        throw IllegalArgumentException("Wrong type viewmodel")
    }
}