package com.aslifitness.fitracker.vendors

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.BuildConfig
import com.aslifitness.fitracker.databinding.ItemVendorViewBinding
import com.aslifitness.fitracker.utils.setTextWithVisibility
import com.aslifitness.fitracker.vendors.data.PhotoInfo
import com.aslifitness.fitracker.vendors.data.VendorInfo

/**
 * Created by shubhampandey
 */
class VendorItemViewHolder(private val binding: ItemVendorViewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(vendorInfo: VendorInfo) {
        configurePhotos(vendorInfo.photos)
        binding.vendorName.setTextWithVisibility(vendorInfo.name)
    }

    private fun configurePhotos(photos: List<PhotoInfo>?) {
        if (!photos.isNullOrEmpty()) {
            val imageUrls = photos.map { getImageUrl(it) }
            binding.banners.setData(imageUrls)
        }
    }

    private fun getImageUrl(photo: PhotoInfo): String {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=${photo.photoReference}&key=${BuildConfig.MAP_API_KEY}"
    }
}