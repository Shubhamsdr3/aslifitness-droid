package com.aslifitness.fitrackers.stories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.ActivityShortVideoBinding
import com.aslifitness.fitrackers.stories.data.StoryDto
import com.aslifitness.fitrackers.stories.data.UserStoryInfo

/**
 * @author Shubham Pandey
 */
class ShortVideoActivity: AppCompatActivity(), StoriesListViewCallback {

    private lateinit var binding: ActivityShortVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShortVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupStoryList()
    }

    private fun setupStoryList() {
        val userStoryList = mutableListOf<StoryDto>()
        userStoryList.add(StoryDto(
            url = "https://cdn.centr.com/content/20000/19828/images/landscapewidemobile2x-cen22-cre-279-buildmuscle-header-16.9.jpg",
            type = "image",
            title = "Centr: chris hemsworth fitness app",
            duration = 2000L
        ))
        userStoryList.add(StoryDto(
            url = "https://us.123rf.com/450wm/mrgarry/mrgarry1607/mrgarry160700022/61823591-male-bodybuilder-fitness-model-trains-in-the-gym.jpg?ver=6",
            type = "image",
            title = "915,510 Fitness Gym Stock Photos and Images - 123RF",
            duration = 2000L
        ))
        userStoryList.add(StoryDto(
            url = "https://images.wallpaperscraft.com/image/single/pullups_man_workout_121789_1600x900.jpg",
            type = "image",
            title = "Centr: chris hemsworth fitness app",
            duration = 2000L
        ))
        userStoryList.add(StoryDto(
            url = "https://assets.gqindia.com/photos/5ff86b6e8fe2b68bc308885c/16:9/w_2560%2Cc_limit/workout.jpg",
            type = "image",
            title = "5 of the shortest and most effective workouts you can do at home | GQ India",
            duration = 2000L
        ))
        userStoryList.add(StoryDto(
            url = "https://cdn.centr.com/content/20000/19828/images/landscapewidemobile2x-cen22-cre-279-buildmuscle-header-16.9.jpg",
            type = "image",
            title = "Centr: chris hemsworth fitness app",
            duration = 2000L
        ))
        val userStoryInfo = UserStoryInfo(header = "Shorts", storiesList = userStoryList)
        binding.stories.setData(userStoryInfo, this)
    }

    override fun onStoryItemClicked(position: Int, userStory: UserStoryInfo) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, ViewStoryFragment.newInstance(position, userStory), ViewStoryFragment::class.simpleName)
        }.commitAllowingStateLoss()
    }
}