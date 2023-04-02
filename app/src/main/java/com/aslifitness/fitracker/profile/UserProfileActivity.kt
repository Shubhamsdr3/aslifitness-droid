package com.aslifitness.fitracker.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitracker.databinding.ActivityUserProfileBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Shubham Pandey
 */

@AndroidEntryPoint
class UserProfileActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(savedInstanceState)
    }

    private fun loadFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(binding.profileContainer.id, UserProfileFragment.newInstance(), UserProfileFragment.TAG)
            }.commit()
        }
    }
}