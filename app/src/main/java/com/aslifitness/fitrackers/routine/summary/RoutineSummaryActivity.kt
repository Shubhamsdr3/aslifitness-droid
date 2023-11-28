package com.aslifitness.fitrackers.routine.summary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.ActivityRoutineSummaryBinding

/**
 * Created by shubhampandey
 */
class RoutineSummaryActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRoutineSummaryBinding

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RoutineSummaryActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutineSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        loadFragment(savedInstanceState)
    }

    private fun loadFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, RoutineSummaryFragment.newInstance(), RoutineSummaryFragment.TAG)
            }.commitAllowingStateLoss()
        }
    }
}