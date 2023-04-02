package com.aslifitness.fitracker.stories

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.FragmentViewStoryBinding
import com.aslifitness.fitracker.stories.data.StoryDto
import com.aslifitness.fitracker.stories.data.UserStoryInfo
import com.aslifitness.fitracker.utils.setTextWithVisibility
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.shts.android.storiesprogressview.StoriesProgressView

/**
 * @author Shubham Pandey
 */
class ViewStoryFragment : Fragment(), StoriesProgressView.StoriesListener {

    private lateinit var binding: FragmentViewStoryBinding
    private var userStoryInfo: UserStoryInfo? = null
    private var currentIndex: Int = 0
    private var pressTime = 0L
    private var limit = 500L

    companion object {
        private const val USER_STORY_INFO = "user_story_info"
        const val IMAGE_TYPE = "image"
        const val VIDEO_TYPE = "video"
        private const val CURRENT_INDEX = "current_index"

        fun newInstance(currentIndex: Int, userStoryInfo: UserStoryInfo) = ViewStoryFragment().apply {
                arguments = bundleOf(
                    Pair(CURRENT_INDEX, currentIndex),
                    Pair(USER_STORY_INFO, userStoryInfo)
                )
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentViewStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureExtras()
        configureStories(userStoryInfo)
        setupListeners()
    }

    private fun setupListeners() {
        binding.reverse.setOnClickListener { binding.progressView.reverse() }
        binding.skip.setOnClickListener { binding.progressView.skip() }
        binding.reverse.setOnTouchListener(onTouchListener)
        binding.skip.setOnTouchListener(onTouchListener)
    }

    @SuppressLint("ClickableViewAccessibility")
    private val onTouchListener = View.OnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pressTime = System.currentTimeMillis()
                userStoryInfo?.storiesList?.getOrNull(currentIndex)?.type?.let { onPauseClicked(it) }
                return@OnTouchListener false
            }
            MotionEvent.ACTION_UP -> {
                val now = System.currentTimeMillis()
                userStoryInfo?.storiesList?.getOrNull(currentIndex)?.type?.let {
                    onResumeClicked(it)
                }
                return@OnTouchListener limit < now - pressTime
            }
        }
        false
    }

    private fun configureExtras() {
        arguments?.let {
            userStoryInfo = it.getParcelable(USER_STORY_INFO)
            currentIndex = it.getInt(CURRENT_INDEX)
        }
    }

    private fun configureListener() {
        binding.icLike.setOnClickListener {
            val color = ContextCompat.getColor(requireContext(), R.color.white)
            binding.icLike.setColorFilter(color)
            val scaleAnimation = ScaleAnimation(
                0f, 1f, 0F, 1f,
                Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5F)
            scaleAnimation.duration = 500
            scaleAnimation.fillAfter = true
            binding.icLike.animation = scaleAnimation
            scaleAnimation.start()
        }
        binding.icShare.setOnClickListener { configureShare() }
    }

    private fun configureShare() {
        val currentItem = userStoryInfo?.storiesList?.get(currentIndex)
        currentItem?.let {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, it.title)
                putExtra(Intent.EXTRA_TEXT, it.url)
            }.also {
                startActivity(Intent.createChooser(it, "Share"))
            }
        }
    }

    private fun configureStories(userStoryInfo: UserStoryInfo?) {
        userStoryInfo?.let {
            configureUserProfile(it)
            configureStoryItem(it.storiesList?.getOrNull(currentIndex))
            val durations = getStoriesCountWithDurations(it.storiesList)
            if (durations.isEmpty()) return
            binding.progressView.setStoriesCountWithDurations(durations)
            binding.progressView.setStoriesListener(this)
            binding.progressView.startStories()
        }
    }

    private fun configureUserProfile(story: UserStoryInfo) {
        Glide.with(requireContext())
            .load(story.profileImage)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.profileImage)
        binding.userName.setTextWithVisibility(story.userName)
        val title = story.storiesList?.get(currentIndex)?.title
        binding.title.setTextWithVisibility(title)
    }

    private fun getStoriesCountWithDurations(userStoryList: List<StoryDto>?): LongArray {
        if (!userStoryList.isNullOrEmpty()) {
            val durations = LongArray(userStoryList.size)
            userStoryList.forEachIndexed { index, storyInfo ->
                if (storyInfo.duration != null) {
                    durations[index] = storyInfo.duration
                }
            }
            return durations
        }
        return longArrayOf()
    }

    private fun configureStoryItem(firstStory: StoryDto?) {
        firstStory?.let {
            when (it.type) {
                IMAGE_TYPE -> configureImageStoryView(firstStory)
                VIDEO_TYPE -> configureVideoStoryView(firstStory)
                else -> {
                    // do nothing
                }
            }
        }
    }

    private fun configureVideoStoryView(story: StoryDto) {
        if (!story.url.isNullOrEmpty()) {
            binding.videoView.setData(story.url)
            binding.videoView.visibility = View.VISIBLE
            binding.imageView.visibility = View.GONE
        }
    }

    private fun configureImageStoryView(story: StoryDto) {
        if (!story.url.isNullOrEmpty()) {
            binding.imageView.setData(story.url)
            binding.videoView.visibility = View.GONE
            binding.imageView.visibility = View.VISIBLE
        }
    }

    override fun onNext() {
        val storyList = userStoryInfo?.storiesList
        val currentItem = storyList?.getOrNull(currentIndex)
        if (currentItem?.type == VIDEO_TYPE) {
            binding.videoView.releasePlayer()
        }
        val nextItem = storyList?.getOrNull(++currentIndex)
        configureStoryItem(nextItem)
    }

    override fun onPrev() {
        val storyList = userStoryInfo?.storiesList
        val currentItem = storyList?.getOrNull(currentIndex)
        if (currentItem?.type == VIDEO_TYPE) {
            binding.videoView.releasePlayer()
        }
        if (currentIndex - 1 < 0)
            return
        val prevItem = storyList?.getOrNull(--currentIndex)
        configureStoryItem(prevItem)
    }

    override fun onComplete() {
        activity?.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.progressView.destroy()
        binding.videoView.releasePlayer()
    }

    private fun onPauseClicked(contentType: String) {
        if (contentType == VIDEO_TYPE) {
            binding.videoView.pause()
        }
        binding.progressView.pause()
    }

    private fun onResumeClicked(contentType: String) {
        if (contentType == VIDEO_TYPE) {
            binding.videoView.play()
        }
        binding.progressView.resume()
    }

}