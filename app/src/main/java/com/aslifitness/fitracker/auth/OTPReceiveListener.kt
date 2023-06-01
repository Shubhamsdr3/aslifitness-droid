package com.aslifitness.fitracker.auth

interface OTPReceiveListener {

    fun onOTPReceived(otp: String?)
}