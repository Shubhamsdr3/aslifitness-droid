package com.aslifitness.fitracker.workoutlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.addworkout.AddWorkoutActivity
import com.aslifitness.fitracker.databinding.ActivityWorkoutListBinding
import com.aslifitness.fitracker.utils.INTENT_NAME
import com.aslifitness.fitracker.utils.INTENT_WORKOUT

/**
 * @author Shubham Pandey
 */
class WorkoutListActivity: AppCompatActivity(), WorkoutFragmentCallback {

    private lateinit var binding: ActivityWorkoutListBinding

    companion object {

        @JvmStatic
        fun start(context: Context, title: String) {
            Intent(context, WorkoutListActivity::class.java).apply {
                putExtra(INTENT_WORKOUT, title)
            }.also {
                context.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val title = intent.extras?.getString(INTENT_WORKOUT)
        loadFragment(savedInstanceState, title)
    }

    private fun loadFragment(savedInstanceState: Bundle?, title: String?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.container, WorkoutListFragment.newInstance(title), WorkoutListFragment.TAG)
            }.commitAllowingStateLoss()
        }
    }

    override fun onWorkoutSelected(workout: String) {
        AddWorkoutActivity.start(this, workout)
    }
}