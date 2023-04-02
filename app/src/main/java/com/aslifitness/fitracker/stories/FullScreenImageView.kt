package com.aslifitness.fitracker.stories

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitracker.databinding.ViewImageStoryBinding
import com.aslifitness.fitracker.utils.setImageWithVisibility

/**
 * @author Shubham Pandey
 */
class FullScreenImageView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0) : ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = ViewImageStoryBinding.inflate(LayoutInflater.from(context), this, true)

    companion object {
        private const val MIN_DISTANCE = 200
    }

    fun setData(imageUrl: String) {
        binding.viewStoryImage.setImageWithVisibility(imageUrl)
    }
}