package com.aslifitness.fitracker.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.ActivityWorkoutDetailBinding
import com.aslifitness.fitracker.utils.INTENT_WORKOUT

/**
 * @author Shubham Pandey
 */
class WorkoutDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutDetailBinding

    companion object {

        @JvmStatic
        fun start(context: Context, workout: String) {
            val intent = Intent(context, WorkoutDetailActivity::class.java)
            intent.putExtra(INTENT_WORKOUT, workout)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val workoutName = intent.getStringExtra(INTENT_WORKOUT)
        if (savedInstanceState == null && !workoutName.isNullOrEmpty()) {
            loadFragment(workoutName)
        }
    }

    private fun loadFragment(workoutName: String) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, WorkoutDetailFragment.newInstance(workoutName), WorkoutDetailFragment.TAG)
        }.commitAllowingStateLoss()
    }
}