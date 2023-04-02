package com.aslifitness.fitracker.vendors.data

import com.aslifitness.fitracker.network.ApiService
import com.aslifitness.fitracker.network.performNetworkCall

class VendorRepository(private val apiService: ApiService) {

    suspend fun fetchNearbyVendors(requestParams: Map<String, Any?>) = performNetworkCall { apiService.fetchVendorList(requestParams) }
}