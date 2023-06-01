package com.aslifitness.fitracker.widgets.auth

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.aslifitness.fitracker.databinding.ViewLoginCardBinding


/**
 * Created by shubhampandey
 */
class SignInCardView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = ViewLoginCardBinding.inflate(LayoutInflater.from(context), this, true)
    private var callback: SignInListener? = null
    private var phoneNumber: String? = null

    init {
        setUpListener()
    }

    fun setSigInCallback(callback: SignInListener) {
        this.callback = callback
    }

    fun setPhoneNumber(phoneNumber: String?) {
        binding.phoneNumber.setText(phoneNumber)
    }

    private fun setUpListener() {
        binding.phoneNumber.addTextChangedListener {
            if (!it.isNullOrEmpty() && it.length >= 10) {
                binding.submitButton.enableButton()
                verifyPhoneNumber(it.toString())
            }
        }
        binding.submitButton.setOnClickListener {
            if (!phoneNumber.isNullOrEmpty()) {
                callback?.onPhoneNumberEntered(phoneNumber!!)
            } else {
                Toast.makeText(context, "Please enter the phone number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verifyPhoneNumber(text: String) {
        if (text.length > 10) {
            phoneNumber = text.substring(text.length - 10, text.length).filter { !it.isWhitespace() }
            callback?.onPhoneNumberEntered(phoneNumber!!)
        } else {
            phoneNumber = text.filter { !it.isWhitespace() }
            callback?.onPhoneNumberEntered(phoneNumber!!)
        }
    }
}