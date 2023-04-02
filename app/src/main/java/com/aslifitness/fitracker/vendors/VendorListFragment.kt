package com.aslifitness.fitracker.vendors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.databinding.FragmentVendorListBinding
import com.aslifitness.fitracker.network.ApiHandler
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.sharedprefs.UserStore
import com.aslifitness.fitracker.vendors.data.VendorInfo
import com.aslifitness.fitracker.vendors.data.VendorRepository
import com.aslifitness.fitracker.vendors.data.VendorsResponseDto

class VendorListFragment : Fragment(), VendorListAdapterCallback {

    private lateinit var binding: FragmentVendorListBinding
    private var viewModel: VendorListViewModel? = null

    companion object {
        fun newInstance() = VendorListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentVendorListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = VendorListViewModelFactory(VendorRepository(ApiHandler.apiService))
        viewModel = ViewModelProvider(this, factory)[VendorListViewModel::class.java]
        viewModel!!.fetchVendors(UserStore.getUserId())
        viewModel!!.vendorListState.observe(viewLifecycleOwner) { onViewStateChanged(it) }
    }

    private fun onViewStateChanged(state: NetworkState<ApiResponse<VendorsResponseDto>>?) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Success -> onResponseSuccess(state.data)
            is NetworkState.Error -> showError()
            else -> {
                // do nothing
            }
        }
    }

    private fun onResponseSuccess(data: ApiResponse<VendorsResponseDto>?) {
        binding.contentLoader.hide()
        data?.data?.run {
            if (!vendors.isNullOrEmpty()) {
                val adapter = VendorListAdapter(vendors, this@VendorListFragment)
                binding.vendorList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                binding.vendorList.adapter = adapter
            }
        }
    }

    private fun showError() {
        binding.contentLoader.hide()
    }

    private fun showLoader() {
        binding.contentLoader.show()
    }

    override fun onItemClicked(data: VendorInfo) {
        Toast.makeText(context, "On item clicked..", Toast.LENGTH_SHORT).show()
    }
}