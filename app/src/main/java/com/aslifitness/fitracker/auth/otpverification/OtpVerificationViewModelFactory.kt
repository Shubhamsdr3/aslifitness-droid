package com.aslifitness.fitracker.auth.otpverification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.network.ApiService

class OtpVerificationViewModelFactory (private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OtpVerificationViewModel::class.java)) {
            return OtpVerificationViewModel(apiService) as T
        }
        throw IllegalStateException("Wrong type viewmodel")
    }
}