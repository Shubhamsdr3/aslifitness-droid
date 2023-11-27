package com.aslifitness.fitracker.auth.otpverification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.network.ApiService
import com.aslifitness.fitracker.profile.UserRepository

class OtpVerificationViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OtpVerificationViewModel::class.java)) {
            return OtpVerificationViewModel(userRepository) as T
        }
        throw IllegalStateException("Wrong type viewmodel")
    }
}