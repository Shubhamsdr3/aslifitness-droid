package com.aslifitness.fitracker.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.network.ApiService

/**
 * @author Shubham Pandey
 */
class ProfileViewModelFactory(private val apiService: ApiService): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            return UserProfileViewModel(apiService) as T
        }
        throw IllegalStateException("Wrong type viewmodel")
    }
}