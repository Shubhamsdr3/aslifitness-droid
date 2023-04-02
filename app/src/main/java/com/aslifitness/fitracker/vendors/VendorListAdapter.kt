package com.aslifitness.fitracker.vendors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.databinding.ItemVendorViewBinding
import com.aslifitness.fitracker.vendors.data.VendorInfo

/**
 * Created by shubhampandey
 */
class VendorListAdapter(private val vendorList: List<VendorInfo>, private val callback: VendorListAdapterCallback): RecyclerView.Adapter<VendorItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorItemViewHolder {
        val itemBinding = ItemVendorViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VendorItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VendorItemViewHolder, position: Int) {
        val vendorInfo = vendorList[position]
        holder.bindData(vendorInfo)
        holder.itemView.setOnClickListener { callback.onItemClicked(vendorInfo) }
    }

    override fun getItemCount() = vendorList.count()
}