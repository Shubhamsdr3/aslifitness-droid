package com.aslifitness.fitrackers.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by shubhampandey
 */
class AddRoutineViewModelFactory(private val repository: RoutineRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddRoutineViewModel::class.java)) {
            return AddRoutineViewModel(repository) as T
        }
        throw IllegalArgumentException("Wrong viewmodel type")
    }
}