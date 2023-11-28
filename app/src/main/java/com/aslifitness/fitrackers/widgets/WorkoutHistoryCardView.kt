package com.aslifitness.fitrackers.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.WorkoutHistoryCardViewBinding
import com.aslifitness.fitrackers.detail.data.WorkoutHistory
import com.aslifitness.fitrackers.model.CardItem
import com.aslifitness.fitrackers.utils.setTextWithVisibility

/**
 * @author Shubham Pandey
 */
class WorkoutHistoryCardView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0) : ConstraintLayout(context, attributeSet, defyStyle), ItemCardViewListener {

    private val binding = WorkoutHistoryCardViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var callback: WorkoutHistoryCallback? = null
    private var dimen0Dp = context.resources.getDimension(R.dimen.dimen_0dp).toInt()

    fun setData(history: WorkoutHistory, callback: WorkoutHistoryCallback) {
        this.callback = callback
        history.run {
            binding.workoutTitle.setTextWithVisibility(header)
            configureWorkoutHistory(rows)
        }
        setupListener()
    }

    private fun setupListener() {
        binding.icPlus.setOnClickListener { callback?.onPlusClicked() }
    }

    private fun configureWorkoutHistory(rows: List<CardItem>?) {
        if (!rows.isNullOrEmpty()) {
            for (item in rows) {
                val cardItem = ItemCardView(context)
                binding.pastWorkouts.addView(cardItem, getLayoutParamsWithMargins())
                cardItem.setData(item, this)
                cardItem.setRightIcon(R.drawable.ic_drop_down_24)
            }
            binding.pastWorkouts.visibility = View.VISIBLE
        } else {
            binding.pastWorkouts.visibility = View.GONE
        }
    }

    private fun getLayoutParamsWithMargins(): LinearLayout.LayoutParams {
        val margin16Dp = context.resources.getDimension(R.dimen.dimen_16dp).toInt()
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(dimen0Dp, margin16Dp, dimen0Dp, margin16Dp)
        return params
    }

    override fun onRightIconClicked(action: String?, actionUrl: String?) {
        // do nothing..
    }
}