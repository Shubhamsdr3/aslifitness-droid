package com.aslifitness.fitrackers.stories

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.databinding.ItemStoryViewBinding
import com.aslifitness.fitrackers.stories.data.StoryDto
import com.aslifitness.fitrackers.utils.setImageWithVisibility
import com.aslifitness.fitrackers.utils.setTextWithVisibility

/**
 * @author Shubham Pandey
 */
class ItemStoryViewHolder(private val binding: ItemStoryViewBinding): RecyclerView.ViewHolder(binding.root)  {

    fun bindData(story: StoryDto) {
        binding.thumbnail.setImageWithVisibility(story.url)
        binding.title.setTextWithVisibility(story.title)
    }
}