package com.aslifitness.fitracker.widgets.banners

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.LayoutBannersBinding
import com.aslifitness.fitracker.utils.Utility
import com.aslifitness.fitracker.utils.setImageWithVisibility

/**
 * Created by shubhampandey
 */
class FitBannerWidget @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): LinearLayout(context, attributeSet, defyStyle), FBannerCallback {

    private val binding = LayoutBannersBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(imageList: List<String>) {
        if (imageList.count() == 1) {
            configureFixedImage(imageList[0])
        } else {
            configureImageList(imageList)
        }
        configureIndicators(imageList)
    }

    private fun configureFixedImage(imageUrl: String) {
        val width = Utility.getViewWidth(6)
        binding.imageLayout.image.layoutParams = FrameLayout.LayoutParams(width, width / 2)
        binding.imageLayout.image.setImageWithVisibility(imageUrl)
    }

    private fun configureImageList(imageList: List<String>) {
        binding.imageList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.imageList.adapter = FBannerAdapter(imageList, this)
        binding.imageList.visibility = View.VISIBLE
    }

    private fun configureIndicators(imageList: List<String>) {
        imageList.forEach { _ ->
            val dotImage = AppCompatImageView(context)
            dotImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dot))
            binding.indicators.addView(dotImage)
        }
    }
}