package com.aslifitness.fitracker.addworkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.ActivityAddWorkoutBinding
import com.aslifitness.fitracker.utils.INTENT_NAME

/**
 * @author Shubham Pandey
 */
class AddWorkoutActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddWorkoutBinding

    companion object {

        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, AddWorkoutActivity::class.java))
        }

        @JvmStatic
        fun start(context: Context, name: String) {
            Intent(context, AddWorkoutActivity::class.java).apply {
                putExtra(INTENT_NAME, name)
            }.also {
                context.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.extras?.getString(INTENT_NAME)
        loadFragment(savedInstanceState, name)
    }

    private fun loadFragment(savedInstanceState: Bundle?, name: String?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, AddWorkoutFragment.newInstance(name), AddWorkoutFragment.TAG)
            }.commitAllowingStateLoss()
        }
    }
}