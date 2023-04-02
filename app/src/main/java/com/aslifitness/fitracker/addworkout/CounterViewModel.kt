package com.aslifitness.fitracker.addworkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author Shubham Pandey
 */
class CounterViewModel(private var count: Int) : ViewModel() {

    private val counterMutableLiveData: MutableLiveData<Int> = MutableLiveData()
    val counterLiveData: LiveData<Int> = counterMutableLiveData

    fun onIncrementClicked() {
        counterMutableLiveData.value = ++count
    }

    fun onDecrementClicked() {
        if (count > 0) {
            counterMutableLiveData.value = --count
        }
    }

    fun getCount() = count
}