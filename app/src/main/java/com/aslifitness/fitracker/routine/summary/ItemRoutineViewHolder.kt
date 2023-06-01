package com.aslifitness.fitracker.routine.summary

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.ItemUserRoutineBinding
import com.aslifitness.fitracker.databinding.ItemWorkoutListBinding
import com.aslifitness.fitracker.routine.data.RoutineWorkout
import com.aslifitness.fitracker.routine.data.UserRoutineDto
import com.aslifitness.fitracker.utils.setCircularImage
import com.aslifitness.fitracker.utils.setImageWithVisibility
import com.aslifitness.fitracker.utils.setTextWithVisibility

/**
 * Created by shubhampandey
 */
class ItemRoutineViewHolder(private val itemBinding: ItemUserRoutineBinding): RecyclerView.ViewHolder(itemBinding.root) {

    fun bindData(data: UserRoutineDto, callback: () -> Unit) {
        data.run {
            itemBinding.title.setTextWithVisibility(title)
            configureWorkouts(workouts)
            itemBinding.dottedLine.setOnClickListener { callback.invoke() }
        }
    }

    private fun configureWorkouts(workouts: List<RoutineWorkout>?) {
        if (workouts.isNullOrEmpty()) return
        workouts.forEach {
            val binding = ItemWorkoutListBinding.inflate(LayoutInflater.from(itemView.context), null, false)
            binding.iconWorkout.setCircularImage(it.image, R.drawable.ic_dumble_new)
            binding.header.setTextWithVisibility(it.title)
            binding.subHeader.setTextWithVisibility(it.subTitle)
            itemBinding.exerciseContainer.addView(binding.root)
        }
    }
}