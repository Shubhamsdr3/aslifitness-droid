package com.aslifitness.fitracker.widgets.banners

import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.databinding.ItemBannerViewBinding
import com.aslifitness.fitracker.utils.Utility
import com.aslifitness.fitracker.utils.setImageWithVisibility

/**
 * Created by shubhampandey
 */
class BannerItemViewHolder(private val binding: ItemBannerViewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(imageUrl: String) {
        val width = Utility.getViewWidth(6)
        binding.image.layoutParams = FrameLayout.LayoutParams(width, width / 2)
        binding.image.setImageWithVisibility(imageUrl)
    }
}