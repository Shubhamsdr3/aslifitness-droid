package com.aslifitness.fitracker.workoutlist

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.addworkout.AddWorkoutActivity
import com.aslifitness.fitracker.addworkout.AddWorkoutFragment
import com.aslifitness.fitracker.addworkout.AddWorkoutFragmentCallback
import com.aslifitness.fitracker.databinding.ActivityWorkoutListBinding
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.model.CtaInfo
import com.aslifitness.fitracker.model.addworkout.NewAddWorkout
import com.aslifitness.fitracker.model.addworkout.WorkoutSetInfo
import com.aslifitness.fitracker.summary.WorkoutSummaryActivity
import com.aslifitness.fitracker.summary.data.WorkoutSummaryResponse
import com.aslifitness.fitracker.utils.INTENT_WORKOUT


/**
 * @author Shubham Pandey
 */
class WorkoutListActivity: AppCompatActivity(), WorkoutFragmentCallback, AddWorkoutFragmentCallback {

    private lateinit var binding: ActivityWorkoutListBinding

    companion object {
        private const val FOCUS_SEARCH = "focus_search"

        @JvmStatic
        fun start(context: Context, title: String) {
            Intent(context, WorkoutListActivity::class.java).apply {
                putExtra(INTENT_WORKOUT, title)
            }.also {
                context.startActivity(it)
            }
        }

        @JvmStatic
        fun startWithAnim(context: Context, title: String) {
            Intent(context, WorkoutListActivity::class.java).apply {
                putExtra(INTENT_WORKOUT, title)
                putExtra(FOCUS_SEARCH, true)
            }.also {
                val options = ActivityOptions.makeSceneTransitionAnimation(context as Activity?)
                context.startActivity(it, options.toBundle())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val title = intent.extras?.getString(INTENT_WORKOUT)
        val focusSearch = intent.extras?.getBoolean(FOCUS_SEARCH)
        loadFragment(savedInstanceState, title, focusSearch)
        onBackPressedDispatcher.addCallback {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                isEnabled = false
                this@WorkoutListActivity.onBackPressed()
            }
        }
    }

    private fun loadFragment(savedInstanceState: Bundle?, title: String?, focusSearch: Boolean?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.container, WorkoutListFragment.newInstance(title, focusSearch), WorkoutListFragment.TAG)
            }.commitAllowingStateLoss()
        }
    }

    override fun onWorkoutSelected(workoutList: List<NewAddWorkout>) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, AddWorkoutFragment.newInstance(workoutList), AddWorkoutFragment.TAG)
            addToBackStack(AddWorkoutFragment.TAG)
        }.commitAllowingStateLoss()
    }

    override fun onWorkoutAdded(data: WorkoutSummaryResponse) {
        WorkoutSummaryActivity.start(this, data)
        finish()
    }
}