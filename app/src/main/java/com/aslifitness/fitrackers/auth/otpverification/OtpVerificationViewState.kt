package com.aslifitness.fitrackers.auth.otpverification

/**
 * @author Shubham Pandey
 */
sealed class OtpVerificationViewState {

    object ShowLoader: OtpVerificationViewState()

    object HideLoader: OtpVerificationViewState()

    object OnUserSaved: OtpVerificationViewState()

    object ShowKeyboard: OtpVerificationViewState()

    data class ShowError(val throwable: Throwable): OtpVerificationViewState()
}