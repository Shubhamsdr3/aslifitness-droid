package com.aslifitness.fitrackers.camera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitrackers.databinding.ActivityCameraSuccessBinding

/**
 * Created by shubhampandey
 */
class CameraSuccessActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCameraSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val imageUri = intent.data
        binding.capturedImage.setImageURI(imageUri)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}