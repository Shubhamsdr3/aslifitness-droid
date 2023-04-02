package com.aslifitness.fitracker.vendors

import com.aslifitness.fitracker.vendors.data.VendorInfo

/**
 * Created by shubhampandey
 */
interface VendorListAdapterCallback {

    fun onItemClicked(data: VendorInfo)
}