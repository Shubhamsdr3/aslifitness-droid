package com.aslifitness.fitracker.profile

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.ItemDashboardViewBinding
import com.aslifitness.fitracker.model.WorkoutDto
import com.aslifitness.fitracker.utils.setImageWithPlaceholder
import com.aslifitness.fitracker.utils.setTextWithVisibility

/**
 * @author Shubham Pandey
 */
class DashboardViewHolder(private val binding: ItemDashboardViewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(item: WorkoutDto) {
        binding.icWorkout.setImageWithPlaceholder(item.image, R.drawable.ic_workout)
        binding.exerciseName.setTextWithVisibility(item.title)
    }
}