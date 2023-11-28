package com.aslifitness.fitrackers.assistant.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.aslifitness.fitrackers.databinding.FitStatsRowBinding
import com.aslifitness.fitrackers.assistant.model.FitActivity
import java.util.*

class FitStatsAdapter : ListAdapter<FitActivity, FitItemViewHolder>(DIFF) {

    private var maxDuration = 0.0

    override fun submitList(list: List<FitActivity>?) {
        list?.forEach {
            maxDuration = Math.max(maxDuration, it.distanceMeters)
        }
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitItemViewHolder {
        val itemBinding = FitStatsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FitItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FitItemViewHolder, position: Int) {
        holder.bind(getItem(position), maxDuration.toInt())
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<FitActivity>() {
            override fun areItemsTheSame(oldItem: FitActivity, newItem: FitActivity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FitActivity, newItem: FitActivity): Boolean {
                return oldItem == newItem
            }
        }
    }
}