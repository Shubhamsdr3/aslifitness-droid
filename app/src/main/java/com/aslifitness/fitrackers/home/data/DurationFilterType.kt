package com.aslifitness.fitrackers.home.data

/**
 * Created by shubhampandey
 */
enum class DurationFilterType(val code: Int, val type: String) {
    WEEK(0, "Weekly"),
    MONTH(1, "Monthly"),
    YEAR(2, "Yearly")
}