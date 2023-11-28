package com.aslifitness.fitrackers.filedownloader

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.offline.DownloadService
import androidx.recyclerview.widget.LinearLayoutManager
import com.aslifitness.fitrackers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val listMediaItem: List<MediaItem> = listOf(
        MediaItem.Builder()
            .setUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")
            .setMimeType(MimeTypes.APPLICATION_MP4)
            .setMediaMetadata(MediaMetadata.Builder().setTitle("Big Buck Bunny MP4").build())
            .build(),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            DownloadService.start(this, MediaDownloadService::class.java)
        } catch (e: IllegalStateException) {
            DownloadService.startForeground(this, MediaDownloadService::class.java)
        }

        val onlineAdapter = OnlineAdapter()
        binding.rvOnlineVideo.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = onlineAdapter
        }
        onlineAdapter.submitList(listMediaItem)
        binding.fabMyDownloads.setOnClickListener {
            startActivity(Intent(this, OfflineVideoActivity::class.java))
        }
    }
}
