package com.aslifitness.fitracker.widgets.routine

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.ItemUserRoutineBinding
import com.aslifitness.fitracker.databinding.ItemWorkoutListBinding
import com.aslifitness.fitracker.routine.data.RoutineWorkout
import com.aslifitness.fitracker.routine.data.UserRoutineDto
import com.aslifitness.fitracker.utils.setCircularImage
import com.aslifitness.fitracker.utils.setTextWithVisibility

/**
 * Created by shubhampandey
 */
class RoutineItemWidget @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = ItemUserRoutineBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(data: UserRoutineDto, callback: () -> Unit) {
        data.run {
            binding.title.setTextWithVisibility(title)
            configureWorkouts(workouts)
            binding.dottedLine.setOnClickListener { callback.invoke() }
        }
    }

    private fun configureWorkouts(workouts: List<RoutineWorkout>?) {
        if (workouts.isNullOrEmpty()) return
        workouts.forEach {
            val itemBinding = ItemWorkoutListBinding.inflate(LayoutInflater.from(context), null, false)
            itemBinding.iconWorkout.setCircularImage(it.image, R.drawable.ic_dumble_new)
            itemBinding.header.setTextWithVisibility(it.title)
            itemBinding.subHeader.setTextWithVisibility(it.subTitle)
            binding.exerciseContainer.addView(binding.root)
        }
    }
}