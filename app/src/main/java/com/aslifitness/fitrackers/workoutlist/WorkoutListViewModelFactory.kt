package com.aslifitness.fitrackers.workoutlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/**
 * @author Shubham Pandey
 */
class WorkoutListViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutListViewModel::class.java)) {
            return WorkoutListViewModel() as T
        }
        throw IllegalArgumentException("Wrong type viewmodel")
    }
}