package com.aslifitness.fitracker.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.WidgetWorkoutListBinding
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.workoutlist.WorkoutListAdapter
import com.aslifitness.fitracker.workoutlist.WorkoutListAdapterCallback

/**
 * @author Shubham Pandey
 */
class WorkoutListWidget @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle), WorkoutListAdapterCallback {

    private val binding = WidgetWorkoutListBinding.inflate(LayoutInflater.from(context), this, true)
    private var callback: WorkoutListWidgetCallback? = null

    fun setData(workoutList: List<Workout>, callback: WorkoutListWidgetCallback) {
        this.callback = callback
        if (workoutList.isNotEmpty()) {
            binding.workoutList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binding.workoutList.adapter = WorkoutListAdapter(workoutList, this)
            configureDivider()
        }
    }

    private fun configureDivider() {
        val divider = ContextCompat.getDrawable(context, R.drawable.line_divider)
        val dimen18Dp = context.resources.getDimension(R.dimen.dimen_18dp)
        divider?.let {
            binding.workoutList.addItemDecoration(ItemOffsetDecoration(dimen18Dp.toInt(), it, RecyclerView.VERTICAL))
        }
    }

    override fun onItemClicked(position: Int, item: Workout) {
        callback?.onWorkoutSelected(item)
    }
}