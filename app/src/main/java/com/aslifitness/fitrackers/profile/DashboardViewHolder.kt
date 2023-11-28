package com.aslifitness.fitrackers.profile

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.ItemDashboardViewBinding
import com.aslifitness.fitrackers.model.WorkoutDto
import com.aslifitness.fitrackers.utils.setImageWithPlaceholder
import com.aslifitness.fitrackers.utils.setTextWithVisibility

/**
 * @author Shubham Pandey
 */
class DashboardViewHolder(private val binding: ItemDashboardViewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(item: WorkoutDto) {
        binding.icWorkout.setImageWithPlaceholder(item.image, R.drawable.ic_dumble_new)
        binding.exerciseName.setTextWithVisibility(item.title)
    }
}