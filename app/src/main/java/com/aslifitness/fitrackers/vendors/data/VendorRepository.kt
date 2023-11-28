package com.aslifitness.fitrackers.vendors.data

import com.aslifitness.fitrackers.network.ApiService
import com.aslifitness.fitrackers.network.performNetworkCall

class VendorRepository(private val apiService: ApiService) {

    suspend fun fetchNearbyVendors(requestParams: Map<String, Any?>) = performNetworkCall { apiService.fetchVendorList(requestParams) }
}