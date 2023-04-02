package com.aslifitness.fitracker.auth.otpverification

import androidx.lifecycle.*
import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.network.ApiService
import com.aslifitness.fitracker.network.performNetworkCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Shubham Pandey
 */

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(private val apiService: ApiService): ViewModel() {

    private val otpMutableViewState = MutableLiveData<OtpVerificationViewState>()
    val otpViewState: LiveData<OtpVerificationViewState> = otpMutableViewState

    fun onContinueClick(otp: String) {
        // do nothing
    }

    fun showKeyboard() {
        viewModelScope.launch {
            delay(200)
            otpMutableViewState.value = OtpVerificationViewState.ShowKeyboard
        }
    }

    fun saveUser(userDto: UserDto) {
        viewModelScope.launch {
            performNetworkCall { apiService.saveUser(userDto) }
                .catch {
                    otpMutableViewState.value = OtpVerificationViewState.ShowError(it)
                }.collect {
                    otpMutableViewState.value = OtpVerificationViewState.OnUserSaved
                }
        }
    }
}