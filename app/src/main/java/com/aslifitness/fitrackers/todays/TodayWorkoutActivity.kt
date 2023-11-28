package com.aslifitness.fitrackers.todays

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.ActivityTodaysWorkoutBinding

/**
 * @author Shubham Pandey
 */
class TodayWorkoutActivity: AppCompatActivity() {

    private lateinit var binding: ActivityTodaysWorkoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodaysWorkoutBinding.inflate(layoutInflater)
        loadFragment(savedInstanceState)
    }

    private fun loadFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.container, TodayWorkoutFragment.newInstance(), TodayWorkoutFragment.TAG)
            }.commitAllowingStateLoss()
        }
    }
}