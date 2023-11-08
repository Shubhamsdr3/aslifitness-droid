package com.aslifitness.fitracker.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.WidgetQuotesBinding
import com.aslifitness.fitracker.model.QuoteInfo

/**
 * Created by shubhampandey
 */
class QuotesWidget @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = WidgetQuotesBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(quoteInfo: QuoteInfo, callback: QuoteWidgetCallback) {
        binding.tvQuote.text = quoteInfo.quote
        binding.tvAuthor.text = quoteInfo.author
        if (quoteInfo.isLiked) {
            binding.icLike.setImageResource(R.drawable.red_heart_icon)
        } else {
            binding.icLike.setImageResource(R.drawable.icon_heart)
        }
        binding.icLike.setOnClickListener { onLikeClicked(quoteInfo, callback) }
        binding.icShare.setOnClickListener {
            Toast.makeText(context, "On Share clicked..", Toast.LENGTH_SHORT).show()
            callback.onShareClicked()
        }
    }

    private fun onLikeClicked(quoteInfo: QuoteInfo, callback: QuoteWidgetCallback) {
        if (quoteInfo.isLiked) {
            quoteInfo.isLiked = false
            binding.icLike.setImageResource(R.drawable.icon_heart)
        } else {
            quoteInfo.isLiked = true
            binding.icLike.setImageResource(R.drawable.red_heart_icon)
        }
        callback.onLikeClicked(quoteInfo.isLiked, quoteId = quoteInfo.id)
    }
}