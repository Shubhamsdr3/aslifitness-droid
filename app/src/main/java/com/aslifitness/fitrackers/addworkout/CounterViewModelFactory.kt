package com.aslifitness.fitrackers.addworkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author Shubham Pandey
 */
class CounterViewModelFactory(private var count: Int): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CounterViewModel::class.java)) {
            return CounterViewModel(count) as T
        }
        throw IllegalArgumentException("Wrong type of viewmodel")
    }
}