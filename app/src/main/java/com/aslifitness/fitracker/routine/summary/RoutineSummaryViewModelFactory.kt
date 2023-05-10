package com.aslifitness.fitracker.routine.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by shubhampandey
 */
class RoutineSummaryViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoutineSummaryViewModel::class.java)) {
//            return RoutineSummaryViewModel() as T
        }
        throw IllegalArgumentException("Wrong type viewmodel")
    }
}