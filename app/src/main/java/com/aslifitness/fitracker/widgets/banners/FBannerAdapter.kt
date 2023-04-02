package com.aslifitness.fitracker.widgets.banners

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.databinding.ItemBannerViewBinding

/**
 * Created by shubhampandey
 */
class FBannerAdapter(private val imageList: List<String>, private val callback: FBannerCallback): RecyclerView.Adapter<BannerItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerItemViewHolder {
        val itemBinding = ItemBannerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BannerItemViewHolder, position: Int) {
        holder.bindData(imageList[position])
        holder.itemView.setOnClickListener {}
    }

    override fun getItemCount() = imageList.count()
}