package com.aslifitness.fitracker.workoutlist

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.databinding.ItemWorkoutListBinding
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.utils.setImageWithVisibility
import com.aslifitness.fitracker.utils.setTextWithVisibility

/**
 * @author Shubham Pandey
 */
class WorkoutItemViewHolder(private val binding: ItemWorkoutListBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(workout: Workout) {
        binding.iconWorkout.setImageWithVisibility(workout.image)
        binding.header.setTextWithVisibility(workout.header)
        binding.subHeader.setTextWithVisibility(workout.subHeader)
    }
}