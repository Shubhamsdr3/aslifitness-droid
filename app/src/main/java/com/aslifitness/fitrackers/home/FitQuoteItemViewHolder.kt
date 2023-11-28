package com.aslifitness.fitrackers.home

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.databinding.ItemFitQuoteViewBinding
import com.aslifitness.fitrackers.model.QuoteInfo
import com.aslifitness.fitrackers.widgets.QuoteWidgetCallback

/**
 * Created by shubhampandey
 */
class FitQuoteItemViewHolder(private val binding: ItemFitQuoteViewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(quoteInfo: QuoteInfo, callback: QuoteWidgetCallback) {
        binding.quoteWidget.setData(quoteInfo, callback)
    }
}