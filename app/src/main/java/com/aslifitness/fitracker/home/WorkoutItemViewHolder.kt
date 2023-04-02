package com.aslifitness.fitracker.home

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.model.WorkoutDto
import com.aslifitness.fitracker.databinding.ItemWorkoutBinding
import com.aslifitness.fitracker.utils.setTextWithVisibility
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