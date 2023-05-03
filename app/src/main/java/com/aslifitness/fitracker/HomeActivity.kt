package com.aslifitness.fitracker

import android.Manifest
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aslifitness.fitracker.addworkout.AddWorkoutBottomSheet
import com.aslifitness.fitracker.addworkout.WorkoutBottomSheetCallback
import com.aslifitness.fitracker.databinding.ActivityHomeBinding
import com.aslifitness.fitracker.model.WorkoutDto
import com.aslifitness.fitracker.model.addworkout.WorkoutBottomSheetInfo
import com.aslifitness.fitracker.routine.UserRoutineActivity
import com.aslifitness.fitracker.utils.EMPTY
import com.aslifitness.fitracker.utils.showShortToast
import com.aslifitness.fitracker.workoutlist.WorkoutListActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), WorkoutBottomSheetCallback {

    private lateinit var binding: ActivityHomeBinding
    private var isChecked = false

    companion object {
        private const val TAG = "FBNotificationService"
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission(),) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        configureLister()
        getFCMToken()
        FirebaseMessaging.getInstance().subscribeToTopic("routine-reminder")
            .addOnCompleteListener { task ->
                showShortToast("Subscribed! You will get all discount offers notifications")
                if (!task.isSuccessful) {
                    showShortToast("Failed! Try again.")
                }
            }
        askNotificationPermission()
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d(TAG, "Firebase message token: $token")
            Toast.makeText(this, "Firebase message token: $token", Toast.LENGTH_SHORT).show()
        })
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
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