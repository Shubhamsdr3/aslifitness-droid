package com.aslifitness.fitracker.assistant.home

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.assistant.model.FitActivity
import com.aslifitness.fitracker.databinding.FitStatsRowBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class FitItemViewHolder(private val binding: FitStatsRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(activity: FitActivity, max: Int) {
            val context = itemView.context
            val calendar = Calendar.getInstance().apply { timeInMillis = activity.date }
            val day = calendar.getDisplayName(
                Calendar.DAY_OF_WEEK,
                Calendar.LONG,
                Locale.getDefault()
            )
            val monthFormatter = SimpleDateFormat("MM", Locale.getDefault())
            binding.statsRowTitle.text = context.getString(
                R.string.stat_date,
                day,
                calendar.get(Calendar.DAY_OF_MONTH),
                monthFormatter.format(calendar.time).toInt()
            )

            val minutes = TimeUnit.MILLISECONDS.toMinutes(activity.durationMs)
            val km = String.format("%.2f", activity.distanceMeters / 1000)
            val duration = context.getString(R.string.stat_duration, minutes)
            val distance = context.getString(R.string.stat_distance, km)
            binding.statsRowContent.apply {
                text = duration
                append("\n")
                append(distance)
            }

            binding.statsRowProgress.max = max
            binding.statsRowProgress.progress = activity.distanceMeters.toInt()
        }
    }