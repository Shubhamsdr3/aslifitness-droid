package com.aslifitness.fitracker.stories

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.databinding.ItemStoryViewBinding
import com.aslifitness.fitracker.stories.data.StoryDto
import com.aslifitness.fitracker.utils.setImageWithVisibility
import com.aslifitness.fitracker.utils.setTextWithVisibility

/**
 * @author Shubham Pandey
 */
class ItemStoryViewHolder(private val binding: ItemStoryViewBinding): RecyclerView.ViewHolder(binding.root)  {

    fun bindData(story: StoryDto) {
        binding.thumbnail.setImageWithVisibility(story.url)
        binding.title.setTextWithVisibility(story.title)
    }
}