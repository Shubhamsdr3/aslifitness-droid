package com.aslifitness.fitrackers.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.aslifitness.fitrackers.databinding.LayoutTitleTextViewBinding

/**
 * Created by shubhampandey
 */
class TitleTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): LinearLayout(context, attributeSet, defyStyle) {

    private val binding = LayoutTitleTextViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setTitle(title: String) {
        binding.key.text = title
    }

    fun setSubtitle(subtitle: String) {
        binding.value.text = subtitle
    }
}