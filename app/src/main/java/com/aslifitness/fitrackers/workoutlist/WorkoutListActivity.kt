package com.aslifitness.fitrackers.workoutlist

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.addworkout.AddWorkoutFragment
import com.aslifitness.fitrackers.addworkout.AddWorkoutFragmentCallback
import com.aslifitness.fitrackers.databinding.ActivityWorkoutListBinding
import com.aslifitness.fitrackers.model.addworkout.NewAddWorkout
import com.aslifitness.fitrackers.summary.WorkoutSummaryActivity
import com.aslifitness.fitrackers.summary.data.WorkoutSummaryResponse
import com.aslifitness.fitrackers.utils.INTENT_WORKOUT


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

    override fun onWorkoutAdded(workoutSummary: WorkoutSummaryResponse) {
        WorkoutSummaryActivity.start(this, workoutSummary)
        finish()
    }
}