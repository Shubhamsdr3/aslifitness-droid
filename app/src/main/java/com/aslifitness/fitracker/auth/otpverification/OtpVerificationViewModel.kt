package com.aslifitness.fitracker.auth.otpverification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.network.performNetworkCall
import com.aslifitness.fitracker.profile.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.Map
import kotlin.collections.mutableMapOf
import kotlin.collections.set

/**
 * @author Shubham Pandey
 */

class OtpVerificationViewModel(private val userRepository: UserRepository): ViewModel() {

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
            otpMutableViewState.value = OtpVerificationViewState.ShowLoader
            performNetworkCall { userRepository.saveUser(userDto) }
                .catch {
                    otpMutableViewState.value = OtpVerificationViewState.ShowError(it)
                }.collect {
                    otpMutableViewState.value = OtpVerificationViewState.OnUserSaved
                }
        }
    }
}