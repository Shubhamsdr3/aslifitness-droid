package com.aslifitness.fitracker

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aslifitness.fitracker.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

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
            isChecked = if (!isChecked) {
                rotateRight()
                true
            } else {
                rotateLeft()
                false
            }
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
}