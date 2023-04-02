package com.aslifitness.fitracker.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.ButtonFitnessBinding
import com.aslifitness.fitracker.utils.setTextWithVisibility


/**
 * @author Shubham Pandey
 */
class FitnessButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attrs, defyStyle) {

    private val binding = ButtonFitnessBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FitnessButton, 0, 0)
        val buttonTitle = typedArray.getString(R.styleable.FitnessButton_button_title)
        val state = typedArray.getInt(R.styleable.FitnessButton_button_state, 0)
        configureButtonState(state)
        setButtonText(buttonTitle)
        typedArray.recycle()
    }

    private fun configureButtonState(state: Int) {
        when(state) {
            0 -> disableButton()
            1 -> enableButton()
        }
    }

    fun enableButton() {
        binding.root.background = ContextCompat.getDrawable(context, R.drawable.rect_primary_bg)
        isEnabled = true
        isClickable = true
    }

    fun disableButton() {
        binding.root.background = ContextCompat.getDrawable(context, R.drawable.rect_grey_button)
        isEnabled = false
        isClickable = false
    }

    fun showLoader() {
        binding.loader.visibility = VISIBLE
        disableButton()
    }

    fun hideLoader() {
        binding.loader.visibility = GONE
        enableButton()
    }

    fun setButtonText(text: String?) {
        binding.btnText.setTextWithVisibility(text)
    }
}