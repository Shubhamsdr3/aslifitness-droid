package com.aslifitness.fitracker.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by shubhampandey
 */
class UserRoutineViewModelFactory(private val repository: UserRoutineRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserRoutineViewModel::class.java)) {
            return UserRoutineViewModel(repository) as T
        }
        throw IllegalStateException("Wrong type viewmodel")
    }
}