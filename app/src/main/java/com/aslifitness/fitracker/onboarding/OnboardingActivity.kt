package com.aslifitness.fitracker.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.ActivityOnboardingBinding

/**
 * Created by shubhampandey
 */
class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(savedInstanceState)
    }

    private fun loadFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.onboarding_container, CreateProfileFragment.newInstance(), CreateProfileFragment.TAG)
            }.commitAllowingStateLoss()
        }
    }
}