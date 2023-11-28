package com.aslifitness.fitrackers.stories

import com.aslifitness.fitrackers.stories.data.UserStoryInfo

/**
 * @author Shubham Pandey
 */
interface StoriesListViewCallback {

    fun onStoryItemClicked(position: Int, userStory: UserStoryInfo)
}