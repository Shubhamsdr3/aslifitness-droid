package com.aslifitness.fitracker.assistant.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.FitStatsFragmentBinding
import com.aslifitness.fitracker.assistant.model.FitRepository
import java.util.concurrent.TimeUnit

/**
 * Fragment to show the last users activities and stats.
 *
 * It observes the total count stats and the last activities updating the view upon changes
 */
class FitStatsFragment : Fragment() {

    lateinit var actionsCallback: FitStatsActions

    private lateinit var binding: FitStatsFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =  FitStatsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FitStatsAdapter()
        binding.statsList.adapter = adapter

        val repository = FitRepository.getInstance(requireContext())
        repository.getStats().observe(viewLifecycleOwner, Observer { fitStats ->
            binding.statsActivityCount.text = getString(
                R.string.stats_total_count,
                fitStats.totalCount
            )
            binding.statsDistanceCount.text = getString(
                R.string.stats_total_distance,
                fitStats.totalDistanceMeters.toInt()
            )
            val durationInMin = TimeUnit.MILLISECONDS.toMinutes(fitStats.totalDurationMs)
            binding.statsDurationCount.text = getString(R.string.stats_total_duration, durationInMin)
        })

        repository.getLastActivities(10).observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            binding.statsList.smoothScrollToPosition(0)
        })

        binding.statsStartButton.setOnClickListener {
            actionsCallback.onStartActivity()
        }
    }

    interface FitStatsActions {
        fun onStartActivity()
    }
}
