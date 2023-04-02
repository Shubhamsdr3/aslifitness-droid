package com.aslifitness.fitracker.vendors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.network.ApiService
import com.aslifitness.fitracker.vendors.data.VendorRepository

/**
 * Created by shubhampandey
 */
class VendorListViewModelFactory(private val repository: VendorRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VendorListViewModel::class.java)) {
            return VendorListViewModel(repository) as T
        }
        throw IllegalStateException("Wrong type viewmodel")
    }
}