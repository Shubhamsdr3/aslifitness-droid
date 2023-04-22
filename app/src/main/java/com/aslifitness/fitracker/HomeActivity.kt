package com.aslifitness.fitracker

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aslifitness.fitracker.addworkout.AddWorkoutBottomSheet
import com.aslifitness.fitracker.addworkout.WorkoutBottomSheetCallback
import com.aslifitness.fitracker.databinding.ActivityHomeBinding
import com.aslifitness.fitracker.model.WorkoutDto
import com.aslifitness.fitracker.model.addworkout.WorkoutBottomSheetInfo
import com.aslifitness.fitracker.routine.UserRoutineActivity
import com.aslifitness.fitracker.utils.EMPTY
import com.aslifitness.fitracker.workoutlist.WorkoutListActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), WorkoutBottomSheetCallback {

    private lateinit var binding: ActivityHomeBinding
    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        configureLister()
    }

    private fun configureLister() {
        binding.fabIcon.setOnClickListener {
            openWorkoutBottomSheet()
//            isChecked = if (!isChecked) {
//                rotateRight()
//                true
//            } else {
//                rotateLeft()
//                false
//            }
        }
    }

    private fun rotateLeft() {
        ObjectAnimator.ofFloat(binding.fabIcon, "rotation", 135f, 0f).setDuration(300).start()
    }

    private fun rotateRight() {
        ObjectAnimator.ofFloat(binding.fabIcon, "rotation", 0f, 135f).setDuration(300).start()
    }

    private fun setupBottomNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun openWorkoutBottomSheet() {
        val workoutInfo = WorkoutBottomSheetInfo(
            "Quick Start",
            WorkoutDto("New Workout"),
            WorkoutDto("New Routine")
        )
        AddWorkoutBottomSheet.newInstance(workoutInfo).show(supportFragmentManager, AddWorkoutBottomSheet.TAG)
    }

    override fun onAddWorkoutClicked() {
        WorkoutListActivity.start(this, EMPTY)
    }

    override fun onAddRoutineClicked() {
        UserRoutineActivity.start(this)
    }
}