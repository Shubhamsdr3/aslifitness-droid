package com.aslifitness.fitrackers.model

/**
 * Created by shubhampandey
 */
data class QuoteResponse(
    val isSuccess: Boolean?,
    val data: List<QuoteInfo>? = null
)