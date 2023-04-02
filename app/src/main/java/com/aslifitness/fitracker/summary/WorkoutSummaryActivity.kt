package com.aslifitness.fitracker.summary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.ActivityWorkoutSummaryBinding
import com.aslifitness.fitracker.summary.data.WorkoutSummaryResponse
import com.aslifitness.fitracker.utils.INTENT_WORKOUT_SUMMARY

/**
 * Created by shubhampandey
 */
class WorkoutSummaryActivity: AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutSummaryBinding

    companion object {
        @JvmStatic
        fun start(context: Context, data: WorkoutSummaryResponse) {
            Intent(context, WorkoutSummaryActivity::class.java).apply {
                putExtra(INTENT_WORKOUT_SUMMARY, data)
            }.also {
                context.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val workoutSummaryData = intent.extras?.getParcelable<WorkoutSummaryResponse>(INTENT_WORKOUT_SUMMARY)
        if (savedInstanceState == null) loadFragment(workoutSummaryData)
    }

    private fun loadFragment(data: WorkoutSummaryResponse?) {
        data?.let {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.summary_container, WorkoutSummaryFragment.newInstance(it), WorkoutSummaryFragment.TAG)
            }.commitAllowingStateLoss()
        }
    }
}