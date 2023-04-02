package com.aslifitness.fitracker.addworkout

/**
 * @author Shubham Pandey
 */
interface CounterFragmentCallback {

    fun onSubmitClicked(position: Int, count: Int, isNewSet: Boolean)
}