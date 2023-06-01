package com.aslifitness.fitracker.routine.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.routine.RoutineRepository

/**
 * Created by shubhampandey
 */
class RoutineViewModelFactory(private val repository: RoutineRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoutineSummaryViewModel::class.java)) {
            return RoutineSummaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Wrong type viewmodel")
    }
}