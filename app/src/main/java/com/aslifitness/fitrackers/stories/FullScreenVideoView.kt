package com.aslifitness.fitrackers.stories

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitrackers.databinding.ViewVideoStoryBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player

/**
 * @author Shubham Pandey
 */
class FullScreenVideoView : ConstraintLayout {

    private lateinit var binding: ViewVideoStoryBinding
    private var exoPlayer: ExoPlayer? = null

    companion object {
        private const val VOLUME_ON = 1f
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        binding = ViewVideoStoryBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setData(videoUrl: String) {
        exoPlayer = ExoPlayer.Builder(context).build()
        binding.playerView.player = exoPlayer
        val mediaItem: MediaItem = MediaItem.fromUri(videoUrl)
        exoPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            volume = VOLUME_ON
        }
        exoPlayer?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                        // do nothing
                    }
                    Player.STATE_ENDED -> {
                        // do nothing
                    }
                    Player.STATE_IDLE -> {
                        // do nothing
                    }
                    Player.STATE_READY -> {
                        // do nothing
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                // do nothing
            }
        })
    }

    fun releasePlayer() {
        exoPlayer?.release()
        exoPlayer?.clearMediaItems()
        exoPlayer = null
        binding.playerView.player = null
    }

    fun pause() {
        exoPlayer?.pause()
    }

    fun play() {
        exoPlayer?.play()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        releasePlayer()
    }
}