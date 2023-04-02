package com.aslifitness.fitracker.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.addworkout.WorkoutRepository

/**
 * Created by shubhampandey
 */
class WorkoutSummaryVMFactory(private val repository: WorkoutRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutSummaryViewModel::class.java)) {
            return WorkoutSummaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Wrong type viewmodel")
    }
}