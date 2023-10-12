package com.aslifitness.fitracker.videopreloading

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private val videoUrl = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        schedulePreloadWork(videoUrl)
        binding.buttonPlayVideo.setOnClickListener {
            startActivity(Intent(this, VideoPlayerActivity::class.java).putExtra("VIDEO_URL", videoUrl))
        }
    }

    private fun schedulePreloadWork(videoUrl: String) {
        val workManager = WorkManager.getInstance(applicationContext)
        val videoPreloadWorker = VideoPreloadWorker.buildWorkRequest(videoUrl)
        workManager.enqueueUniqueWork(
            "VideoPreloadWorker",
            ExistingWorkPolicy.KEEP,
            videoPreloadWorker
        )
    }
}