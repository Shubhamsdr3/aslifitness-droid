package com.aslifitness.fitrackers.addworkout

/**
 * @author Shubham Pandey
 */
interface CounterFragmentCallback {

    fun onSubmitClicked(position: Int, count: Int, isNewSet: Boolean)
}