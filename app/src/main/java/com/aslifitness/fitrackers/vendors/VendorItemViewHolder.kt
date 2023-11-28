package com.aslifitness.fitrackers.vendors

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.BuildConfig
import com.aslifitness.fitrackers.databinding.ItemVendorViewBinding
import com.aslifitness.fitrackers.utils.setTextWithVisibility
import com.aslifitness.fitrackers.vendors.data.PhotoInfo
import com.aslifitness.fitrackers.vendors.data.VendorInfo

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