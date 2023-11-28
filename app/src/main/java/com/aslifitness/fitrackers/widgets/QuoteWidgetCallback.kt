package com.aslifitness.fitrackers.widgets

/**
 * Created by shubhampandey
 */
interface QuoteWidgetCallback {

    fun onLikeClicked(isLiked: Boolean, quoteId: Int)

    fun onShareClicked()
}