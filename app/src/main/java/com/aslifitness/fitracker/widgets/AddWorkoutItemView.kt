package com.aslifitness.fitracker.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitracker.databinding.ItemAddWorkoutBinding
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.model.SetCountInfo
import com.aslifitness.fitracker.utils.setTextWithVisibility

/**
 * @author Shubham Pandey
 */
class AddWorkoutItemView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle), WorkoutQtySelectorCallback {

    private val binding = ItemAddWorkoutBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(workout: Workout) {
        workout.run {
            binding.name.setTextWithVisibility(workout.header)
            configureSetCount(workout.qtyInfo)
        }
    }

    private fun configureSetCount(count: SetCountInfo?) {
        count?.let { binding.setCounter.setData(it, this) }
    }

    override fun onIncrementClicked() {
        // do nothing
    }

    override fun onDecrementClicked() {
        // do nothing
    }
}