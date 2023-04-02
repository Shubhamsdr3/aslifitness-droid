package com.aslifitness.fitracker.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitracker.databinding.ViewCardWithTitleBinding
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.model.AddWorkoutDto
import com.aslifitness.fitracker.model.CardItem
import com.aslifitness.fitracker.model.ItemAddWorkout
import com.aslifitness.fitracker.model.WorkoutResponse
import com.aslifitness.fitracker.utils.setTextWithVisibility

/**
 * @author Shubham Pandey
 */
class ItemCardViewWithTitle @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0):
    ConstraintLayout(context, attributeSet, defyStyle), ItemCardViewListener {

    private val binding = ViewCardWithTitleBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(itemAddWorkout: ItemAddWorkout) {
        itemAddWorkout.run {
            binding.itemTitle.setTextWithVisibility(header)
            configureWorkout(item)
        }
    }

    private fun configureWorkout(item: Workout?) {
        item?.let { binding.itemCard.setData(it) }
    }

    override fun onRightIconClicked(action: String?, actionUrl: String?) {
        // do nothing...
    }
}