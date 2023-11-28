package com.aslifitness.fitrackers.workoutlist

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.ItemWorkoutListBinding
import com.aslifitness.fitrackers.detail.data.Workout
import com.aslifitness.fitrackers.utils.setCircularImage
import com.aslifitness.fitrackers.utils.setTextWithVisibility

/**
 * @author Shubham Pandey
 */
class WorkoutItemViewHolder(private val binding: ItemWorkoutListBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(workout: Workout) {
        binding.iconWorkout.setCircularImage(workout.image, R.drawable.ic_dumble_new)
        binding.header.setTextWithVisibility(workout.header)
        binding.subHeader.setTextWithVisibility(workout.subHeader)
        if (workout.isSelected) {
            binding.icSelected.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_check_active))
        } else {
            binding.icSelected.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_check_disable))
        }
    }
}