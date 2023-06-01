package com.aslifitness.fitracker.widgets.auth.otpinput

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.utils.EMPTY
import com.aslifitness.fitracker.utils.Utility
import java.util.regex.Pattern

/**
 * @author Shubham
 */

class OTPInputField @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): LinearLayout(context, attributeSet, defyStyle) {
    
    companion object {
        private const val OTP_LENGTH = 6
        private const val PATTERN = "[1234567890]*"
    }

    private var itemViews = mutableListOf<OTPItemView>()
    private var otpChildEditText: OTPChildEditText? = null
    private var headerText: AppCompatTextView? = null
    private var errorText: AppCompatTextView? = null
    private var otpListener: OTPListener? = null

    private var otpLength: Int = OTP_LENGTH

    private val filter: InputFilter
        get() = InputFilter { source, start, end, _, _, _ ->
            for (i in start until end) {
                if (!Pattern.compile(PATTERN).matcher(source[i].toString()).matches()) {
                    return@InputFilter EMPTY
                }
            }
            null
        }

    val otp: String
        get() = otpChildEditText?.text?.toString()?.trim() ?: EMPTY

    init {
        configureInputFields()
    }

    private fun configureInputFields() {
        val inputContainerLayoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val inputContainer = FrameLayout(context)
        inputContainer.layoutParams = inputContainerLayoutParams

        // hidden edit text.
        val editTextLayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        editTextLayoutParams.gravity = Gravity.CENTER
        otpChildEditText = OTPChildEditText(context)
        otpChildEditText?.filters = arrayOf(filter, InputFilter.LengthFilter(otpLength))
        setTextWatcher(otpChildEditText)
        otpChildEditText?.isFocusable = true
        otpChildEditText?.isFocusableInTouchMode = true
        inputContainer.addView(otpChildEditText, editTextLayoutParams)

        // Digits/Box container
        val boxesLinearLayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        boxesLinearLayoutParams.setMargins(0, Utility.dpToPx(4), 0, 0)
        val boxesLinearLayout = LinearLayout(context)
        boxesLinearLayout.orientation = HORIZONTAL
        inputContainer.addView(boxesLinearLayout, boxesLinearLayoutParams)

        val otpBoxWidth = Utility.getViewWidth(1)
        val otpBoxHeight = (otpBoxWidth * 0.75f).toInt()
        val spaceBetweenBoxes = (Utility.getDeviceWidth() - Utility.dpToPx(32) - (otpBoxWidth * 6)) / 5
        val paramsBox = LayoutParams(otpBoxWidth, otpBoxHeight)
        paramsBox.setMargins(0, 0, spaceBetweenBoxes.toInt(), 0)
        val paramsLastBox = LayoutParams(otpBoxWidth, otpBoxHeight)

        for (idx in 0 until otpLength) {
            val input = OTPItemView(context)
            input.setViewState(OTPItemView.INACTIVE)
            if (idx == otpLength - 1) {
                boxesLinearLayout.addView(input, paramsLastBox)
            } else {
                boxesLinearLayout.addView(input, paramsBox)
            }
            itemViews.add(input)
        }
        addView(inputContainer)
        setOTP(otp)
    }

    private fun setTextWatcher(otpChildEditText: OTPChildEditText?) {
        otpChildEditText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                errorText?.visibility = INVISIBLE
                setOTP(s)
                setFocus(s.length)
                if (s.length == otpLength) {
                    otpListener?.onOTPComplete(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing
            }
        })
    }

    private fun setFocus(length: Int) {
        if (errorText?.visibility != VISIBLE) {
            itemViews.let {
                for (i in it.indices) {
                    if (i == length) {
                        it[i].setViewState(OTPItemView.ACTIVE)
                    } else {
                        it[i].setViewState(OTPItemView.INACTIVE)
                    }
                }
                if (length == it.size) {
                    it[it.size - 1].setViewState(OTPItemView.ACTIVE)
                }
            }
        }
    }

    fun setInitialState() {
        initState()
        otpChildEditText?.setOnFocusChangeListener { _, focus ->
            if (focus) {
                setFocus(otp.length)
            } else {
                initState()
            }
        }
    }

    private fun initState() {
        if (errorText?.visibility != VISIBLE) {
            itemViews.let {
                for (i in it.indices) {
                    it[i].setViewState(OTPItemView.INACTIVE)
                }
            }
        }
    }

    fun setOTP(s: CharSequence) {
        itemViews.let {
            for (i in it.indices) {
                if (i < s.length) {
                    it[i].setText(s[i].toString())
                } else {
                    it[i].setText(EMPTY)
                }
            }
        }
    }

    fun setCallback(otpListener: OTPListener) {
        this.otpListener = otpListener
    }

    fun requestFocusOTP() {
        otpChildEditText?.requestFocus()
    }

    fun setHeader(header: String) {
        headerText?.run {
            text = header
            visibility = VISIBLE
        }
    }

    fun showError(error: String) {
        showError()
        setErrorMessage(error)
    }

    private fun hideError() {
        errorText?.run {
            text = EMPTY
            visibility = INVISIBLE
        }
    }

    private fun setErrorMessage(error: String) {
        if (error.isNotBlank()) {
            errorText?.run {
                text = error
                visibility = VISIBLE
            }
        } else {
            hideError()
        }
    }

    fun showError() {
        itemViews.let {
            for (itemView in it) {
                itemView.setViewState(OTPItemView.ERROR)
            }
        }
        shakeViewAnim()
    }

    private fun shakeViewAnim() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.shake)
        startAnimation(animation)
    }

    fun resetState() {
        setFocus(otp.length)
        errorText?.visibility = INVISIBLE
    }

    fun showSuccess() {
        itemViews.let {
            for (itemView in it) {
                itemView.setViewState(OTPItemView.SUCCESS)
            }
        }
    }

    private fun setOTP(otp: String) {
        otpChildEditText?.apply {
            text?.clear()
            setText(otp)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setOnTouchListener(l: OnTouchListener) {
        super.setOnTouchListener(l)
        otpChildEditText?.setOnTouchListener(l)
    }

    fun getInputField() = otpChildEditText
}