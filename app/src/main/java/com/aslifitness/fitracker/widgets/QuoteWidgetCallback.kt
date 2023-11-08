package com.aslifitness.fitracker.widgets

/**
 * Created by shubhampandey
 */
interface QuoteWidgetCallback {

    fun onLikeClicked(isLiked: Boolean, quoteId: Int)

    fun onShareClicked()
}