package com.aslifitness.fitrackers.auth

interface OTPReceiveListener {

    fun onOTPReceived(otp: String?)
}