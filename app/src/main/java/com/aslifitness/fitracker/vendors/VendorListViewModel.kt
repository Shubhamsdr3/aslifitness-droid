package com.aslifitness.fitracker.vendors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitracker.model.Location
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.vendors.data.VendorRepository
import com.aslifitness.fitracker.vendors.data.VendorsResponseDto
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class VendorListViewModel(private val vendorRepository: VendorRepository): ViewModel() {

    private val _vendorListState = MutableLiveData<NetworkState<ApiResponse<VendorsResponseDto>>>()
    val vendorListState: LiveData<NetworkState<ApiResponse<VendorsResponseDto>>> = _vendorListState

    fun fetchVendors(userId: String) {
        viewModelScope.launch {
            val requestParams = mutableMapOf<String, Any?>()
            requestParams["userId"] = "gwerbgerwbgerg"
            requestParams["location"] = Location(28.425158F, 77.047516F)
            vendorRepository.fetchNearbyVendors(requestParams)
                .catch {
                    _vendorListState.value = NetworkState.Error(it)
                }.collect { response ->
                    _vendorListState.value = response
                }
        }
    }
}