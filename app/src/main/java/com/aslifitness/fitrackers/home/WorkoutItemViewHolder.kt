package com.aslifitness.fitrackers.home

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.model.WorkoutDto
import com.aslifitness.fitrackers.databinding.ItemWorkoutBinding
import com.aslifitness.fitrackers.utils.setTextWithVisibility
import com.bumptech.glide.Glide

/**
 * @author Shubham Pandey
 */
class WorkoutItemViewHolder(private val binding: ItemWorkoutBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: WorkoutDto) {
        binding.title.setTextWithVisibility(data.title)
        Glide.with(binding.root.context).load(data.image).into(binding.image)
    }
}