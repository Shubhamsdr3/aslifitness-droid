package com.aslifitness.fitrackers.vendors

import com.aslifitness.fitrackers.vendors.data.VendorInfo

/**
 * Created by shubhampandey
 */
interface VendorListAdapterCallback {

    fun onItemClicked(data: VendorInfo)
}