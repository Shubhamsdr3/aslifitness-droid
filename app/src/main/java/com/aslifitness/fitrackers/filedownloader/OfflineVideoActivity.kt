package com.aslifitness.fitrackers.filedownloader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aslifitness.fitrackers.databinding.ActivityDownloadedVideoBinding

class OfflineVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDownloadedVideoBinding

    private val offlineVideoViewModel by lazy {
        ViewModelProvider(this)[OfflineVideoViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadedVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val downloadAdapter = OfflineVideoAdapter()
        binding.rvDownloadedVideo.apply {
            layoutManager = LinearLayoutManager(this@OfflineVideoActivity)
            adapter = downloadAdapter
        }
        offlineVideoViewModel.downloads.observe(this) {
            downloadAdapter.submitList(it.toList())
        }
    }

    override fun onStart() {
        super.onStart()
        offlineVideoViewModel.startFlow(this)
    }

    override fun onPause() {
        super.onPause()
        offlineVideoViewModel.stopFlow()
    }
}
