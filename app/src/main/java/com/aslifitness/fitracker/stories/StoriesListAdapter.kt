package com.aslifitness.fitracker.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.databinding.ItemStoryViewBinding
import com.aslifitness.fitracker.stories.data.StoryDto

/**
 * @author Shubham Pandey
 */
class StoriesListAdapter(private val storyList: List<StoryDto>, private val callback: StoryListAdapterCallback): RecyclerView.Adapter<ItemStoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemStoryViewHolder {
        val binding = ItemStoryViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemStoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemStoryViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val item = storyList[position]
            holder.bindData(item)
            holder.itemView.setOnClickListener { callback.onItemClicked(position) }
        }
    }

    override fun getItemCount() = storyList.count()
}