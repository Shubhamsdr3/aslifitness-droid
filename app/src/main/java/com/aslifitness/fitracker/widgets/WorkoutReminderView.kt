package com.aslifitness.fitracker.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitracker.databinding.LayoutWorkoutReminderBinding

/**
 * Created by shubhampandey
 */
class WorkoutReminderView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = LayoutWorkoutReminderBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        initView()
    }

    private fun initView() {

    }
}