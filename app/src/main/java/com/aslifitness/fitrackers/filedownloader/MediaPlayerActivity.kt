package com.aslifitness.fitrackers.filedownloader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.ActivityFileDownloadBinding
import com.aslifitness.fitrackers.filedownloader.data.MediaItemTag
import com.aslifitness.fitrackers.widgets.PieProgressDrawable
import com.google.android.material.snackbar.Snackbar

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class MediaPlayerActivity : AppCompatActivity(), DownloadTracker.Listener {

    private lateinit var binding: ActivityFileDownloadBinding

    private lateinit var exoPlayer: ExoPlayer

    private var isPlayerPlaying = true

    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private var isFullscreen = false

    private val pieProgressDrawable: PieProgressDrawable by lazy {
        PieProgressDrawable().apply {
            setColor(ContextCompat.getColor(this@MediaPlayerActivity, R.color.colorAccent))
        }
    }

    private val playerViewModel by lazy {
        ViewModelProvider(this)[PlayerViewModel::class.java]
    }

    private val mediaItem: MediaItem by lazy {
        MediaItem.Builder()
            .setUri(intent.getStringExtra(OnlineAdapter.BUNDLE_URL))
            .setMimeType(intent.getStringExtra(OnlineAdapter.BUNDLE_MIME_TYPES))
            .setMediaMetadata(
                MediaMetadata.Builder().setTitle(intent.getStringExtra(OnlineAdapter.BUNDLE_TITLE)).build()
            )
            .setTag(MediaItemTag(-1, intent.getStringExtra(OnlineAdapter.BUNDLE_TITLE)!!))
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DownloadUtil.getDownloadTracker(this).addListener(this)
        binding.downloadState.apply {
            setOnClickListener {
                if(DownloadUtil.getDownloadTracker(context).isDownloaded(mediaItem)) {
                    Snackbar.make(
                        this.rootView, "You've already downloaded the video", Snackbar.LENGTH_SHORT).setAction("Delete") {
                        DownloadUtil.getDownloadTracker(this@MediaPlayerActivity).removeDownload(mediaItem.localConfiguration?.uri)
                    }.show()
                } else {
                    val item = mediaItem.buildUpon()
                        .setTag((mediaItem.localConfiguration?.tag as MediaItemTag).copy(duration = exoPlayer.duration))
                        .build()
                    if(!DownloadUtil.getDownloadTracker(this@MediaPlayerActivity).hasDownload(item.localConfiguration?.uri)) {
                        DownloadUtil.getDownloadTracker(this@MediaPlayerActivity).toggleDownloadDialogHelper(this@MediaPlayerActivity, item)
                    } else {
                        DownloadUtil.getDownloadTracker(this@MediaPlayerActivity)
                            .toggleDownloadPopupMenu(
                                this@MediaPlayerActivity,
                                this,
                                item.localConfiguration?.uri
                            )
                    }
                }
            }
        }
    }

    override fun onDownloadsChanged(download: Download) {
        when (download.state) {
            Download.STATE_DOWNLOADING -> {
                if(binding.downloadState.drawable !is PieProgressDrawable) {
                    binding.downloadState.setImageDrawable(pieProgressDrawable)
                }
                playerViewModel.startFlow(this, download.request.uri)
            }
            Download.STATE_QUEUED, Download.STATE_STOPPED -> {
                binding.downloadState.setImageDrawable(
                    AppCompatResources.getDrawable(this, R.drawable.ic_pause)
                )
            }
            Download.STATE_COMPLETED -> {
                binding.downloadState.setImageDrawable(
                    AppCompatResources.getDrawable(this, R.drawable.ic_download_done)
                )
            }
            Download.STATE_REMOVING -> {
                binding.downloadState.setImageDrawable(
                    AppCompatResources.getDrawable(this, R.drawable.ic_download_24)
                )
            }
            Download.STATE_FAILED, Download.STATE_RESTARTING -> {}
        }
    }

    private fun initPlayer() {
        val downloadRequest: DownloadRequest? = DownloadUtil.getDownloadTracker(this)
            .getDownloadRequest(mediaItem.localConfiguration?.uri)

        exoPlayer = ExoPlayer.Builder(this)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(DownloadUtil.getReadOnlyDataSourceFactory(this))
            ).build()
            .apply {
                playWhenReady = isPlayerPlaying
                seekTo(currentWindow, playbackPosition)
                setMediaItem(maybeSetDownloadProperties(mediaItem, downloadRequest), false)
                prepare()
            }
        binding.playerView.player = exoPlayer
    }

    private fun maybeSetDownloadProperties(item: MediaItem, downloadRequest: DownloadRequest?): MediaItem {
        if (downloadRequest == null) {
            return item
        }
        val builder = item.buildUpon()
            .setMediaId(downloadRequest.id)
            .setUri(downloadRequest.uri)
            .setCustomCacheKey(downloadRequest.customCacheKey)
            .setMimeType(downloadRequest.mimeType)
            .setStreamKeys(downloadRequest.streamKeys)

        val drmConfiguration = item.localConfiguration!!.drmConfiguration
        if (drmConfiguration != null) {
            builder.setDrmConfiguration(
                drmConfiguration.buildUpon().setKeySetId(downloadRequest.keySetId).build()
            )
        }
        return builder.build()
    }

    override fun onStart() {
        super.onStart()
        DownloadUtil.getDownloadTracker(this).downloads[mediaItem.localConfiguration?.uri!!]?.let {
            onDownloadsChanged(it)
        }
        if(Util.SDK_INT > 23) {
            initPlayer()
            binding.playerView.onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        if(Util.SDK_INT <= 23) {
            initPlayer()
            binding.playerView.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if(Util.SDK_INT <= 23) {
            binding.playerView.onPause()
            releasePlayer()
        }
    }

    override fun onStop() {
        playerViewModel.stopFlow()
        super.onStop()
        if(Util.SDK_INT > 23) {
            binding.playerView.onPause()
            releasePlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DownloadUtil.getDownloadTracker(this).removeListener(this)
    }

    private fun releasePlayer() {
        isPlayerPlaying = exoPlayer.playWhenReady
        playbackPosition = exoPlayer.currentPosition
        currentWindow = exoPlayer.currentMediaItemIndex
        exoPlayer.release()
    }
}