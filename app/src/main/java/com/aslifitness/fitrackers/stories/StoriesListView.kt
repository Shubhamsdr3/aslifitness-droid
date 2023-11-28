package com.aslifitness.fitrackers.stories

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.databinding.ViewStoriesListBinding
import com.aslifitness.fitrackers.stories.data.StoryDto
import com.aslifitness.fitrackers.stories.data.UserStoryInfo
import com.aslifitness.fitrackers.utils.setTextWithVisibility

/**
 * @author Shubham Pandey
 */
class StoriesListView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle), StoryListAdapterCallback {

    private val binding = ViewStoriesListBinding.inflate(LayoutInflater.from(context), this, true)
    private var callback: StoriesListViewCallback? = null
    private var userStoryInfo: UserStoryInfo? = null

    fun setData(userStoryInfo: UserStoryInfo, callback: StoriesListViewCallback) {
        this.callback = callback
        this.userStoryInfo = userStoryInfo
        binding.header.setTextWithVisibility(userStoryInfo.header)
        configureStoriesList(userStoryInfo.storiesList)
    }

    private fun configureStoriesList(storiesList: List<StoryDto>?) {
        if (!storiesList.isNullOrEmpty()) {
            binding.shorts.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            binding.shorts.adapter = StoriesListAdapter(storiesList, this)
            binding.shorts.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    outRect.right = 24
                }
            })
        }
    }

    override fun onItemClicked(position: Int) {
        userStoryInfo?.let { callback?.onStoryItemClicked(position, it) }
    }
}