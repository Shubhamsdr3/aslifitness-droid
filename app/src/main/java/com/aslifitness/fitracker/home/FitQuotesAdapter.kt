package com.aslifitness.fitracker.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.databinding.ItemFitQuoteViewBinding
import com.aslifitness.fitracker.model.QuoteInfo
import com.aslifitness.fitracker.widgets.QuoteWidgetCallback

/**
 * Created by shubhampandey
 */
class FitQuotesAdapter(private val itemList: List<QuoteInfo>, private val callback: QuoteWidgetCallback): RecyclerView.Adapter<FitQuoteItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitQuoteItemViewHolder {
        val itemBinding = ItemFitQuoteViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FitQuoteItemViewHolder(itemBinding)
    }

    override fun getItemCount() = itemList.count()

    override fun onBindViewHolder(holder: FitQuoteItemViewHolder, position: Int) {
        holder.bind(itemList[position], callback)
    }
}