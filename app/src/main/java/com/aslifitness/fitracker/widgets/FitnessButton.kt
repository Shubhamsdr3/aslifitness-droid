package com.aslifitness.fitracker.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
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
        setButtonText(buttonTitle)
        typedArray.recycle()
    }

    fun showLoader() {
        binding.loader.visibility = VISIBLE
    }

    fun hideLoader() {
        binding.loader.visibility = GONE
    }

    fun setButtonText(text: String?) {
        binding.btnText.setTextWithVisibility(text)
    }
}