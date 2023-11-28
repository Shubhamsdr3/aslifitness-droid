package com.aslifitness.fitrackers.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitrackers.databinding.ActivityCreateNewWorkoutBinding

/**
 * Created by shubhampandey
 */
class CreateNewWorkoutActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCreateNewWorkoutBinding

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CreateNewWorkoutActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}