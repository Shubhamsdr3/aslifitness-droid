package com.aslifitness.fitracker.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.addworkout.WorkoutRepository

/**
 * Created by shubhampandey
 */
class AddRoutineViewModelFactory(private val repository: WorkoutRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddRoutineViewModel::class.java)) {
            return AddRoutineViewModel(repository) as T
        }
        throw IllegalArgumentException("Wrong viewmodel type")
    }
}