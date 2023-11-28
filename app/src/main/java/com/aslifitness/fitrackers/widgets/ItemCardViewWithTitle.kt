package com.aslifitness.fitrackers.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitrackers.databinding.ViewCardWithTitleBinding
import com.aslifitness.fitrackers.detail.data.Workout
import com.aslifitness.fitrackers.model.ItemAddWorkout
import com.aslifitness.fitrackers.utils.setTextWithVisibility

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