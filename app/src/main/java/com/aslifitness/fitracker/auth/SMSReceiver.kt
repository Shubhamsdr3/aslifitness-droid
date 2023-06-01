package com.aslifitness.fitracker.auth

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import java.util.regex.Pattern

/**
 * Created by shubhampandey
 */

class SMSReceiver : BroadcastReceiver() {

    private var otpListener: OTPReceiveListener? = null

    fun setOTPListener(otpListener: OTPReceiveListener?) {
        this.otpListener = otpListener
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
            val extras = intent.extras
            val status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status?
            if (status?.statusCode == CommonStatusCodes.SUCCESS) {
                val sms = extras?.getString(SmsRetriever.EXTRA_SMS_MESSAGE)
                verifySms(sms)
            }
        }
    }

    private fun verifySms(sms: String?) {
        if (!sms.isNullOrEmpty()) {
            //Pattern.compile("[0-9]+") check a pattern with only digit
            val p = Pattern.compile("\\d+")
            val m = p.matcher(sms)
            if (m.find()) {
                otpListener?.onOTPReceived(m.group())
            }
        }
    }
}