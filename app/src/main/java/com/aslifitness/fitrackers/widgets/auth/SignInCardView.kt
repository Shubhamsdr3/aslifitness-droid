package com.aslifitness.fitrackers.widgets.auth

import android.content.Context
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.aslifitness.fitrackers.databinding.ViewLoginCardBinding


/**
 * Created by shubhampandey
 */
class SignInCardView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = ViewLoginCardBinding.inflate(LayoutInflater.from(context), this, true)
    private var callback: SignInListener? = null
    private var phoneNumber: String? = null

    init {
        setupView()
        setUpListener()
    }

    private fun setupView() {
        binding.tncText.isClickable = true
        binding.tncText.movementMethod = LinkMovementMethod.getInstance()
//        val text = "<a href=\"https://myportfolio-6438d.web.app/\">By creating passcode you agree with our Terms and Conditions and Privacy Policy</a>"
        val spanned: Spanned = Html.fromHtml(context.getString(com.aslifitness.fitrackers.R.string.tnc_text))
        binding.tncText.setText(spanned)
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
        phoneNumber = if (text.length > 10) {
            text.substring(text.length - 10, text.length).filter { !it.isWhitespace() }
        } else {
            text.filter { !it.isWhitespace() }
        }
    }
}