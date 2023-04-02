package com.aslifitness.fitracker.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author Shubham Pandey
 */
class WorkoutDetailViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutDetailViewModel::class.java)) {
            return WorkoutDetailViewModel() as T
        }
        throw IllegalArgumentException("Wrong type viewmodel")
    }
}