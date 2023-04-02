package com.aslifitness.fitracker.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitracker.databinding.ViewLoginCardBinding

/**
 * @author Shubham Pandey
 */
class FitrackerLoginCardView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = ViewLoginCardBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData() {
        // do nothing
    }
}