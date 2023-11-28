package com.aslifitness.fitrackers.addworkout

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.assistant.BiiIntents
import com.aslifitness.fitrackers.databinding.ActivityAddWorkoutBinding
import com.aslifitness.fitrackers.model.CtaInfo
import com.aslifitness.fitrackers.model.addworkout.NewAddWorkout
import com.aslifitness.fitrackers.model.addworkout.WorkoutSetInfo
import com.aslifitness.fitrackers.summary.data.WorkoutSummaryResponse
import com.aslifitness.fitrackers.utils.INTENT_NEW_WORKOUT

/**
 * @author Shubham Pandey
 */
class AddWorkoutActivity: AppCompatActivity(), AddWorkoutFragmentCallback {

    private lateinit var binding: ActivityAddWorkoutBinding

    companion object {

        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, AddWorkoutActivity::class.java))
        }

        @JvmStatic
        fun start(context: Context, newAddWorkout: NewAddWorkout) {
            Intent(context, AddWorkoutActivity::class.java).apply {
                putExtra(INTENT_NEW_WORKOUT, newAddWorkout)
            }.also {
                context.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.extras?.getParcelable<NewAddWorkout>(INTENT_NEW_WORKOUT)
        if (name == null) {
            handleDeeplink(savedInstanceState, intent.data)
        } else {
            loadFragment(savedInstanceState, name)
        }
    }

    private fun handleDeeplink(savedInstanceState: Bundle?, data: Uri?) {
        val workoutName = data?.getQueryParameter(BiiIntents.EXERCISE_NAME)
        val addNewAddWorkout = NewAddWorkout(
            image = "",
            title = workoutName,
            subTitle = "",
            sets = listOf(WorkoutSetInfo(
                0,
                repsCount = 0,
                isDone = false
            )),
            addSetCta = CtaInfo("Add Set")
        )
        loadFragment(savedInstanceState, addNewAddWorkout)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }

    override fun onWorkoutAdded(workoutSummary: WorkoutSummaryResponse) {
        // do nothing.
    }

    private fun loadFragment(savedInstanceState: Bundle?, workout: NewAddWorkout?) {
        if (savedInstanceState == null && workout != null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, AddWorkoutFragment.newInstance(listOf(workout)), AddWorkoutFragment.TAG)
                addToBackStack(AddWorkoutFragment.TAG)
            }.commitAllowingStateLoss()
        }
    }
}