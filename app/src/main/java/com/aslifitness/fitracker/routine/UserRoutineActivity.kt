package com.aslifitness.fitracker.routine

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.ActivityUserRoutineBinding
import com.aslifitness.fitracker.model.addworkout.NewAddWorkout
import com.aslifitness.fitracker.workoutlist.WorkoutFragmentCallback
import com.aslifitness.fitracker.workoutlist.WorkoutListFragment

/**
 * Created by shubhampandey
 */
class UserRoutineActivity: AppCompatActivity(), AddRoutineFragmentCallback, WorkoutFragmentCallback {

    private lateinit var binding: ActivityUserRoutineBinding

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, UserRoutineActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserRoutineBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.backArrow.setOnClickListener { onBackPressed() }
        loadFragment(savedInstanceState)
    }

    private fun loadFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.routine_container, AddRoutineFragment.newInstance(), AddRoutineFragment.TAG)
            }.commitAllowingStateLoss()
        }
    }

    override fun onAddExerciseClicked() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.routine_container, WorkoutListFragment.newInstance(), WorkoutListFragment.TAG)
        }.commitAllowingStateLoss()
    }

    override fun onCancelClicked() {
        finish()
    }

    override fun onWorkoutSelected(workoutList: List<NewAddWorkout>) {
        val workoutListFragment = supportFragmentManager.findFragmentByTag(WorkoutListFragment.TAG)
        if (workoutListFragment is WorkoutListFragment) {
            supportFragmentManager.beginTransaction().remove(workoutListFragment).commitAllowingStateLoss()
        }
        val fragment = supportFragmentManager.findFragmentByTag(AddRoutineFragment.TAG)
        if (fragment is AddRoutineFragment) {
            val routineWorkouts = workoutList.map { it.getRoutineWorkout() }
            fragment.addWorkouts(routineWorkouts)
        }
    }
}