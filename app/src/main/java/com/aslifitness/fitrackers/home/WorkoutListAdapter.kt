package com.aslifitness.fitrackers.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.model.WorkoutDto
import com.aslifitness.fitrackers.databinding.ItemWorkoutBinding

/**
 * @author Shubham Pandey
 */
class WorkoutListAdapter(private val workoutList: List<WorkoutDto>, private val callback: WorkOutAdapterCallback): RecyclerView.Adapter<WorkoutItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutItemViewHolder {
        val binding = ItemWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutItemViewHolder, position: Int) {
        if (isValidPosition(position)) {
            val item = workoutList[position]
            holder.bind(item)
            holder.itemView.setOnClickListener { callback.onWorkoutSelected(item) }
        }
    }

    private fun isValidPosition(position: Int): Boolean {
        return position != RecyclerView.NO_POSITION && position < workoutList.size
    }

    override fun getItemCount() = workoutList.count()
}