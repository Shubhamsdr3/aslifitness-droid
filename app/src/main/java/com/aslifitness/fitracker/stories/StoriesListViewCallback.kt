package com.aslifitness.fitracker.stories

import com.aslifitness.fitracker.stories.data.UserStoryInfo

/**
 * @author Shubham Pandey
 */
interface StoriesListViewCallback {

    fun onStoryItemClicked(position: Int, userStory: UserStoryInfo)
}