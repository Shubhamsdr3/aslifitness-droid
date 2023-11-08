package com.aslifitness.fitracker.home

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.databinding.ItemFitQuoteViewBinding
import com.aslifitness.fitracker.model.QuoteInfo
import com.aslifitness.fitracker.widgets.QuoteWidgetCallback

/**
 * Created by shubhampandey
 */
class FitQuoteItemViewHolder(private val binding: ItemFitQuoteViewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(quoteInfo: QuoteInfo, callback: QuoteWidgetCallback) {
        binding.quoteWidget.setData(quoteInfo, callback)
    }
}