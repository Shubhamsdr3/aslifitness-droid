package com.aslifitness.fitracker.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.db.UserDao
import com.aslifitness.fitracker.network.ApiService
import com.aslifitness.fitracker.profile.UserRepository
import java.lang.IllegalArgumentException

/**
 * Created by shubhampandey
 */
class CreateProfileViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateProfileViewModel::class.java)) {
            return CreateProfileViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Wrong viewmodel type.")
    }
}